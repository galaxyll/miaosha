package com.yxh.miaosha.result;

/**
 * @author galaxy
 * @date 20-2-9 - 下午3:33
 */
public class CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通用错误对象
     */
    public static CodeMsg SUCCESS= new CodeMsg(200,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500,"服务器异常");

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
