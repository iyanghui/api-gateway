package pers.zhixilang.yway.core.exception;

/**
 * 服务未找到
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:50
 */
public class RouteNotFoundException extends RuntimeException {
    private String msg;

    public RouteNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
