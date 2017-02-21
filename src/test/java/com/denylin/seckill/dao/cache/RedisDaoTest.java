package com.denylin.seckill.dao.cache;

import com.denylin.seckill.dao.SeckillDao;
import com.denylin.seckill.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by DYL on 2017/2/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {
    private long id = 1003;

    @Autowired
    private  RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception{
        // get and put
        Seckill seckill = redisDao.getSeckill(id);
        if (seckill == null){
            seckill = seckillDao.queryById(id);
            if (seckill != null){
                String result = redisDao.putSeckill(seckill);
                System.out.println("redis进行缓存：" + seckill);
                seckill = redisDao.getSeckill(id);
                System.out.println("从redis缓存中获取：" + seckill);
            }
        } else {
            System.out.println("redis中存在：" + seckill);
        }
    }
}