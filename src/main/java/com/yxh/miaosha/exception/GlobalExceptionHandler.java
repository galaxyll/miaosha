package com.yxh.miaosha.exception;

import com.yxh.miaosha.result.CodeMsg;
import com.yxh.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author galaxy
 * @date 20-2-11 - 上午11:26
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest httpServletRequest, Exception e){

        if (e instanceof GlobalException){
            GlobalException exception = (GlobalException)e;
            return Result.error(exception.getCodeMsg());
        }

        if (e instanceof BindException){
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

}
