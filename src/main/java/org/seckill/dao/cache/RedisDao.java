package org.seckill.dao.cache;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/31
 * Description:
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(RedisDao.class);

    private JedisPool jedisPool;

    private static final String KEYPRE = "seckill:";

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    // 从Redis缓存中获取Seckill对象
    public Seckill getSeckill(long seckillId) {
        Jedis jedis = jedisPool.getResource();
        String key = KEYPRE + seckillId;
        // protostuff序列化 pojo
        byte[] bytes = jedis.get(key.getBytes());
        if (bytes != null) {
            // 反序列化
            Seckill seckill = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
            return seckill;
        }

        // 缓存中未获取到，从数据库中获取，并存入Redis缓存,在调用层处理
        jedis.close();
        return null;
    }

    public String putSeckill(Seckill seckill) {
        String key = KEYPRE + seckill.getSeckillId();
        Jedis jedis = jedisPool.getResource();

        byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        int timeout = 60 * 60;
        String result = jedis.setex(key.getBytes(), timeout, bytes);
        jedis.close();
        return result;
    }


}
