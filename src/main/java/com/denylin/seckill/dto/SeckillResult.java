package com.denylin.seckill.dto;

/**
 * 所有ajax请求返回类型，封装json结果
 * Created by DYL on 2017/2/10.
 */
public class SeckillResult<T> {

    private boolean success;

    private T data;

    private String errors;

    public SeckillResult(boolean success, String errors) {
        this.success = success;
        this.errors = errors;
    }

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
