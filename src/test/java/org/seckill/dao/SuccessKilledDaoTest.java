package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int result = successKilledDao.insertSuccessKilled(1000L, 13100239751L);
        System.out.println("result = " + result);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000L;
        long userPhone = 13100239751L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, userPhone);
        System.out.println("test output: " + successKilled.getSeckill());

    }

}