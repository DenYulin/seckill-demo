package com.denylin.seckill.dao;

import com.denylin.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.xml.crypto.Data;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 配置spring和junit整合，junit启动时加载springIoC容器
 * Created by DYL on 2017/2/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    // 注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void reduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println(updateCount);
    }

    @Test
    public void queryById() throws Exception {
        /**
         * org.mybatis.spring.MyBatisSystemException: nested exception is org.apache.ibatis.binding.BindingException:
         * Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
         * 原因：
         * List<Seckill> queryAll(int offset, int limit); - > queryAll(arg0, arg1)
         * 应改为：
         * List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
         */
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        /**
         * 1000秒杀iphone7
         * Seckill{
         * seckilled=1000,
         * name='1000秒杀iphone7',
         * number=100,
         * startTime=Thu Feb 09 00:00:00 CST 2017,
         * endTime=Sat Feb 11 00:00:00 CST 2017,
         * createTime=Thu Feb 09 01:58:04 CST 2017}
         */
    }

    @Test
    public void queryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckillList){
            System.out.println(seckill);
        }
        /**
         * Seckill{seckilled=1000, name='1000秒杀iphone7', number=100, startTime=Thu Feb 09 00:00:00 CST 2017, endTime=Sat Feb 11 00:00:00 CST 2017, createTime=Thu Feb 09 01:58:04 CST 2017}
         * Seckill{seckilled=1001, name='1000秒杀ipad4', number=200, startTime=Thu Feb 09 00:00:00 CST 2017, endTime=Sat Feb 11 00:00:00 CST 2017, createTime=Thu Feb 09 01:58:04 CST 2017}
         * Seckill{seckilled=1002, name='1000秒杀nubia z11', number=300, startTime=Thu Feb 09 00:00:00 CST 2017, endTime=Sat Feb 11 00:00:00 CST 2017, createTime=Thu Feb 09 01:58:04 CST 2017}
         * Seckill{seckilled=1003, name='1000秒杀神船 k660d', number=400, startTime=Thu Feb 09 00:00:00 CST 2017, endTime=Sat Feb 11 00:00:00 CST 2017, createTime=Thu Feb 09 01:58:04 CST 2017}
         */
    }

}