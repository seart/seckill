package org.seckill.exception;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description:
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }


}
