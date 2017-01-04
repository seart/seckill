package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/24
 * Description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private Logger logger = LoggerFactory.getLogger(SeckillServiceTest.class);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list = {}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill = {}", seckill);
        System.out.println("seckill = " + seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        logger.info("exposer = {}", exposer);
        /*
        exposer = Exposer{exposed=true, md5='51b06c5abd87a93d924315d7487fb7aa', seckillId=1000, now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phone = 13112312311L;
        String md5 = "51b06c5abd87a93d924315d7487fb7aa";
        SeckillExecution execution = seckillService.executeSeckill(id, phone, md5);
        logger.info("result= {} ", execution);
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long seckillId = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            logger.info("{}", exposer);
            long userPhone = 11111678912L;
            String md5 = exposer.getMd5();

            try {

                SeckillExecution execution = seckillService.executeSeckill(seckillId, userPhone, md5);
                logger.info("{}", execution);
            } catch (SeckillCloseException e) {
                logger.error("exception {}", e.getMessage());
            } catch (RepeatKillException e) {
                logger.error("exception {}", e.getMessage());
            }
        } else {
            logger.warn("秒杀没有开启！");
        }

    }

    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1001;
        long phone = 15473342256L;
        // 开发秒杀
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);

        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }

    }

}