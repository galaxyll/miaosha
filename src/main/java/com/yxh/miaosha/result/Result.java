package com.yxh.miaosha.result;

/**
 * @author galaxy
 * @date 20-2-9 - 下午3:09
 * 结果类，用来将响应状态发送到客户端
 */
public class    Result <T>{

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {
        if (codeMsg==null){
            return;
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 响应状态
     */
    private  int code;
    /**
     * 响应消息
     */
    private  String msg;
    /**
     * 响应数据
     */
    private  T data;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }


    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMsg codeMsg){
        return new Result<>(codeMsg);
    }
}
