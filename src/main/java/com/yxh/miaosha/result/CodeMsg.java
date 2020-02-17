package com.yxh.miaosha.result;

/**
 * @author galaxy
 * @date 20-2-9 - 下午3:33
 */
public class    CodeMsg {
    private int code;
    private String msg;

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通用错误对象
     */
    public static CodeMsg SUCCESS= new CodeMsg(0,"success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500,"服务器异常");
    public static CodeMsg PARAM_EMPTY = new CodeMsg(501,"参数为空");
    public static CodeMsg PARAM_ERROR = new CodeMsg(502,"参数格式错误");
    public static CodeMsg ACCOUNT_NOT_FOUND = new CodeMsg(400,"账号不存在");
    public static CodeMsg SESSION_ERROR = new CodeMsg(502,"session失效或不存在");

    public static CodeMsg BIND_ERROR = new CodeMsg(505,"参数校验异常：%s");
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(504,"秒杀商品已售空");
    public static CodeMsg MIAOSHA_REPEAT = new CodeMsg(506,"请勿重复秒杀");
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(507,"订单不存在");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(510,"请求非法");
    public static CodeMsg MIAOSHA_ERROR = new CodeMsg(515,"秒杀错误");
    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object... objects){
        int code = this.code;
        String message = String.format(this.msg,objects);
        return new CodeMsg(code,message);
    }

}
