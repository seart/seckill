package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/23
 * Description:
 */
public interface SeckillDao {

    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);


    Seckill queryById(long seckillId);

    /*java没有保存形参的记录，在运行期offset,limit参会变成arg0,arg1,
    * 而我们写的sql语句用的是形参，这样会出现绑定错误
    * 可以用@Param指定参数名，这样运行期就不会出现绑定参数错误
    * 有多个参数的时候加@Param
    * */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);


    void killByProcedure(Map<String, Object> paramMap);

}
