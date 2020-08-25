package com.sise.pet.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.sise.pet.entity.User;
import com.sise.pet.entity.WebLog;
import com.sise.pet.service.IUserService;
import com.sise.pet.service.IWebLogService;
import com.sise.pet.shiro.JWTUtil;
import com.sise.pet.utils.HttpRequestUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    private static final String TOKEN = "Authentication";

    @Resource
    IWebLogService webLogService;

    @Resource
    IUserService userService;

    @Pointcut("execution(public * com.sise.pet.controller..*.*(..))")
    public void pointCut() {
    }

    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
    }

    @AfterReturning(value = "pointCut()", returning = "ret")
    public void doAfterReturning(Object ret) throws Throwable {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //获取当前请求对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //记录请求信息(通过Logstash传入Elasticsearch)
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        String token = request.getHeader(TOKEN);
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            webLog.setDescription(log.value());
        }
        long endTime = System.currentTimeMillis();
        String urlStr = request.getRequestURL().toString();
        webLog.setBasePath(StrUtil.removeSuffix(urlStr, URLUtil.url(urlStr).getPath()));
        webLog.setIp(request.getRemoteUser());
        webLog.setMethod(request.getMethod());
        webLog.setParameter(JSON.toJSONString(getParameter(method, joinPoint.getArgs())));
        webLog.setResult(JSON.toJSONString(result));
        webLog.setSpendTime((int) (endTime - startTime));
        webLog.setStartTime(startTime);
        webLog.setUri(request.getRequestURI());
        webLog.setUrl(request.getRequestURL().toString());
        webLog.setIp(HttpRequestUtil.getIpAddress(request));
        if (StrUtil.isNotBlank(token)) {
            String userId = JWTUtil.getUserId(token);
            if (StrUtil.isNotBlank(userId)) {
                User user = userService.getById(userId);
                webLog.setUsername(user != null ? user.getUsername() : "");
            }

        }

        webLogService.save(webLog);
        /*Map<String,Object> logMap = new HashMap<>();
        logMap.put("url",webLog.getUrl());
        logMap.put("method",webLog.getMethod());
        logMap.put("parameter",webLog.getParameter());
        logMap.put("spendTime",webLog.getSpendTime());
        logMap.put("description",webLog.getDescription());
        log.info(Markers.appendEntries(logMap), JSONUtil.parse(webLog).toString());*/
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            /*RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }*/
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StringUtils.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            } else {
                argList.add(args[i]);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }
}
