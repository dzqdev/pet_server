package com.sise.pet.aspect;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.sise.pet.annotation.Log;
import com.sise.pet.entity.WebLog;
import com.sise.pet.security.security.TokenProvider;
import com.sise.pet.service.IWebLogService;
import com.sise.pet.utils.HttpRequestUtil;
import com.sise.pet.utils.SecurityUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Aspect
@Component
@Order(1)
public class WebLogAspect {

    @Resource
    IWebLogService webLogService;

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
        WebLog webLog = new WebLog();
        Object result = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(Log.class)) {
            Log log = method.getAnnotation(Log.class);
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
        String username = SecurityUtils.getCurrentUsername();
        webLog.setUsername(username);
        webLogService.save(webLog);
        return result;
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.stream(args);
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        return logArgs;
    }
}
