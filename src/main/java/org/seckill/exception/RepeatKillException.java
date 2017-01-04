package org.seckill.exception;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description: 重复秒杀异常，是一个运行期异常，不需要手动try catch
 * MySQL 只支持运行期异常的回滚操作
 */
public class RepeatKillException extends SeckillException{

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
