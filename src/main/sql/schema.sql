-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
  `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY idx_start_time(start_time),
  KEY idx_end_time(end_time),
  KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

-- 要是出现了 1293错误码，可以将create_time字段提前到start_time前面，等创建表后再修改


-- 初始化数据
INSERT INTO seckill(name,number,start_time,end_time)
VALUES
  ('1000秒杀iphone7',1000, '2017-02-09 00:00:00','2017-02-11 00:00:00'),
  ('2000秒杀ipad4',2000, '2017-02-09 00:00:00','2017-02-11 00:00:00'),
  ('3000秒杀nubia z11',3000, '2017-02-09 00:00:00','2017-02-11 00:00:00'),
  ('4000秒杀神船 k660d',4000, '2017-02-09 00:00:00','2017-02-11 00:00:00');

-- 秒杀成功明细表
-- 用户登陆认证相关的信息
CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态标示：-1:无效 0:成功 1:已付款 2:已发货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone), /*联合主键*/
  KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';

-- 连接数据库控制台

-- 为什么手写DDL
-- 记录每次上线的DDL修改
-- 上线V1.1
-- ALTER TABLE seckill
-- DROP INDEX idx_create_time
-- add index idx_c_s(start_time,create_time)

-- 上线V1.2
-- ddl