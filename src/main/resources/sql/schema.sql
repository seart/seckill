create database seckill;

use seckill;

CREATE TABLE `seckill` (
  `seckill_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `name` varchar(120) NOT NULL,
  `number` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`seckill_id`),
  KEY `idx_start_time` (`start_time`),
  KEY `idx_end_time` (`end_time`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8;

insert INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iphone6', 100, '2016-12-23 00:00:00', '2016-12-24 00:00:00'),
  ('500元秒杀ipad2', 100, '2016-12-23 00:00:00', '2016-12-24 00:00:00'),
  ('300元秒杀小米4', 100, '2016-12-23 00:00:00', '2016-12-24 00:00:00'),
  ('200元秒杀红米note', 100, '2016-12-23 00:00:00', '2016-12-24 00:00:00');


create table success_killed(
  seckill_id bigint not null,
  user_phone bigint not null,
  state tinyint not null DEFAULT -1,
  create_time TIMESTAMP not null ,
  PRIMARY KEY (seckill_id, user_phone),  /*联合主键*/
  key idx_create_time(create_time)
)ENGINE=InnoDB auto_increment=1000 DEFAULT charset=utf8;