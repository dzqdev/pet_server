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

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.sise.pet.core.CommonResult;
import com.sise.pet.core.ResultCode;
import com.sise.pet.security.config.SecurityProperties;
import com.sise.pet.security.dto.OnlineUserDto;
import com.sise.pet.security.service.OnlineUserService;
import com.sise.pet.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author /
 */
@Slf4j
public class TokenFilter extends GenericFilterBean {

   private final TokenProvider tokenProvider;
   private final SecurityProperties properties;
   private final OnlineUserService onlineUserService;

   public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService) {
      this.properties = properties;
      this.onlineUserService = onlineUserService;
      this.tokenProvider = tokenProvider;
   }

   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
           throws IOException, ServletException {
      try {
         HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
         String token = resolveToken(httpServletRequest);
         OnlineUserDto onlineUserDto = null;
         if (StrUtil.isNotBlank(token)) {
            //token不为空且token合法
            onlineUserDto = onlineUserService.getOne(properties.getOnlineKey() + token);
            if(null != onlineUserDto && tokenProvider.validateToken(token)){
               Authentication authentication = tokenProvider.getAuthentication(token);
               SecurityContextHolder.getContext().setAuthentication(authentication);
               tokenProvider.checkRenewal(token);
            }
         }
         filterChain.doFilter(servletRequest, servletResponse);
      } catch (Exception e) {
         logger.error(e.getMessage());
         servletResponse.setCharacterEncoding("utf-8");
         servletResponse.getWriter().print(JSONObject.toJSON(CommonResult.failed(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR.getMessage())));
      }
   }

   private String resolveToken(HttpServletRequest request) {
      SecurityProperties properties = SpringContextHolder.getBean(SecurityProperties.class);
      String bearerToken = request.getHeader(properties.getHeader());
      if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
         // 去掉令牌前缀
         return bearerToken.replace(properties.getTokenStartWith(), "");
      } else {
         log.debug("非法Token：{}", bearerToken);
      }
      return null;
   }
}
