package cn.my.crawler.util;

/**
 * 
 * 说明：通用状态结果
 * 
 * @author 刘品呈
 * @version 1.0
 * @date 2016年4月21日
 */
public class StateResult {

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应内容
    private Object data;

    // 没用用摆设
    public StateResult() {

    }

    // 返回ok带一个数据用
    public StateResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    // 返回错误消息并且带数据用
    public StateResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    // 返回200 不带数据
    public static StateResult ok() {
        return new StateResult(null);
    }

    // 返回200 带数据
    public static StateResult ok(Object data) {
        return new StateResult(data);
    }

    // 返回错误代码和数据
    public static StateResult build(Integer status, String msg, Object data) {
        return new StateResult(status, msg, data);
    }

    // 返回错误代码和不带数据
    public static StateResult build(Integer status, String msg) {
        return new StateResult(status, msg, null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
