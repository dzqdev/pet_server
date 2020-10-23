/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.sise.pet.security.security;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.sise.pet.security.config.SecurityProperties;
import com.sise.pet.utils.RedisUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author /
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

   @Resource
   private SecurityProperties properties;
   private static final String AUTHORITIES_KEY = "auth";
   @Resource
   private RedisUtils redisUtils;
   private Key key;


   @Override
   public void afterPropertiesSet() {
      byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
      this.key = Keys.hmacShaKeyFor(keyBytes);
   }

   public String createToken(Authentication authentication) {
      String authorities = authentication.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .collect(Collectors.joining(","));

      long now = (new Date()).getTime();
      Date validity = new Date(now + properties.getTokenValidityInSeconds());

      return Jwts.builder()
              .setSubject(authentication.getName())
              .claim(AUTHORITIES_KEY, authorities)
              .signWith(key, SignatureAlgorithm.HS512)
              .setExpiration(validity)
              .compact();
   }

   public Authentication getAuthentication(String token) {
      Claims claims = Jwts.parser()
              .setSigningKey(key)
              .parseClaimsJws(token)
              .getBody();

      Collection<? extends GrantedAuthority> authorities =
              Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                      .map(SimpleGrantedAuthority::new)
                      .collect(Collectors.toList());

      User principal = new User(claims.getSubject(), "", authorities);

      return new UsernamePasswordAuthenticationToken(principal, token, authorities);
   }

   boolean validateToken(String authToken) {
      try {
         Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
         return true;
      } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
         log.info("Invalid JWT signature.");
         e.printStackTrace();
      } catch (ExpiredJwtException e) {
         log.info("Expired JWT token.");
         e.printStackTrace();
      } catch (UnsupportedJwtException e) {
         log.info("Unsupported JWT token.");
         e.printStackTrace();
      } catch (IllegalArgumentException e) {
         log.info("JWT token compact of handler are invalid.");
         e.printStackTrace();
      }
      return false;
   }

   public String getToken(HttpServletRequest request){
      final String requestHeader = request.getHeader(properties.getHeader());
      if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
         return requestHeader.substring(7);
      }
      return null;
   }

   public void checkRenewal(String token) {
      // 判断是否续期token,计算token的过期时间
      long time = redisUtils.getExpire(properties.getOnlineKey() + token) * 1000;
      Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
      // 判断当前时间与过期时间的时间差
      long differ = expireDate.getTime() - System.currentTimeMillis();
      // 如果在续期检查的范围内，则续期
      if (differ <= properties.getDetect()) {
         long renew = time + properties.getRenew();
         redisUtils.expire(properties.getOnlineKey() + token, renew, TimeUnit.MILLISECONDS);
      }
   }
}
