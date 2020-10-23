package com.sise.pet.exception;

import com.sise.pet.core.CommonResult;
import com.sise.pet.core.ResultCode;
import com.sise.pet.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ApiException.class)
    public CommonResult handle(ApiException e) {
        log.error(ThrowableUtil.getStackTrace(e));
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        log.error(ThrowableUtil.getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return CommonResult.failed(ResultCode.FAILURE,message);
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public CommonResult entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return CommonResult.failed(ResultCode.FAILURE,e.getMessage());
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public CommonResult entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return CommonResult.failed(ResultCode.FAILURE,e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public CommonResult handleThrowable(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return CommonResult.failed(ResultCode.FAILURE,e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handleException(Exception e){
        log.error(ThrowableUtil.getStackTrace(e));
        return CommonResult.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

}
