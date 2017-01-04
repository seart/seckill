package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;

import java.util.List;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description:
 */
public interface SeckillService {

    List<Seckill> getSeckillList();


    Seckill getById(long seckillId);


    // 开发商品的秒杀url
    Exposer exportSeckillUrl(long seckillId);

    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException;


    SeckillExecution executeSeckillProcedure(long seckillId, long userPhone, String md5);

}
