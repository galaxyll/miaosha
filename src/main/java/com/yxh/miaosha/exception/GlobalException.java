package com.yxh.miaosha.exception;

import com.yxh.miaosha.result.CodeMsg;

/**
 * @author galaxy
 * @date 20-2-11 - 下午12:09
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID= 1L;

    private CodeMsg codeMsg;

    public GlobalException(CodeMsg codeMsg){
        super(codeMsg.toString());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
