package com.denylin.seckill.exception;

/**
 * 秒杀相关业务异常
 * Created by DYL on 2017/2/10.
 */
public class SeckillException extends RuntimeException{

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
