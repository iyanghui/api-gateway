package pers.zhixilang.yway.core.exception;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-23 16:23
 */
public class ServiceUnableException extends RuntimeException {
    private String msg;

    public ServiceUnableException(String msg) {
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
