package com.denylin.seckill.service;

import com.denylin.seckill.dto.Exposer;
import com.denylin.seckill.dto.SeckillExecution;
import com.denylin.seckill.entity.Seckill;
import com.denylin.seckill.exception.RepeatKillException;
import com.denylin.seckill.exception.SeckillCloseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by DYL on 2017/2/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"
})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill = {}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1001L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        /**
         * Exposer{
         * exposed=true,
         * md5='62df123525ba8cf7e4c5e19f449baab7',
         * seckillId=1001,
         * now=0,
         * start=0,
         * end=0}
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1001L;
        long phone = 13535793578L;
        String md5 = "62df123525ba8cf7e4c5e19f449baab7";
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
            logger.info("result={}", seckillExecution);
        } catch (RepeatKillException e){
            logger.info(e.getMessage());
        } catch (SeckillCloseException e){
            logger.info(e.getMessage());
        }
        /**
         * SeckillExecution{
         * seckillId=1001,
         * state=1,
         * stateInfo='秒杀成功',
         * successKilled=SuccessKilled{
         *      seckillId=1001,
         *      userPhone=13535793578,
         *      state=0,
         *      createTime=Fri Feb 10 15:48:45 CST 2017
         *      }
         * }
         */
    }

    // 测试代码完整逻辑，注意可重复执行
    @Test
    public void testSeckillLogic() throws Exception{
        long id = 1000L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()){
            logger.info("exposer={}", exposer);
            long phone = 18833338888L;
            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", seckillExecution);
            } catch (RepeatKillException e){
                logger.error(e.getMessage());
            } catch (SeckillCloseException e){
                logger.error(e.getMessage());
            }
        } else {
            // 秒杀未开启
            logger.warn("exposer={}", exposer);
        }

        /**
         * SeckillExecution{
         * seckillId=1000,
         * state=1,
         * stateInfo='秒杀成功',
         * successKilled=SuccessKilled{
         *      seckillId=1000,
         *      userPhone=13535793578,
         *      state=0,
         *      createTime=Fri Feb 10 15:58:03 CST 2017
         *      }
         * }
         */
    }

    @Test
    public void executeSeckillProcedure(){
        long seckillId = 1000;
        long phone = 13666661122L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            String md5 = exposer.getMd5();
            SeckillExecution execution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
            logger.info(execution.getStateInfo());
        }
    }

}