package org.seckill.exception;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description:
 */
public class SeckillCloseException extends SeckillException {
    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
