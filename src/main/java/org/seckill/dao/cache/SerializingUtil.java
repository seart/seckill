package org.seckill.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Author: hutaishi@qq.com
 * Date: 2016/12/31
 * Description:
 */
public class SerializingUtil {
    private static Logger logger = LoggerFactory.getLogger(SerializingUtil.class);

    public static byte[] serialize(Object source) {
        ByteArrayOutputStream byteOut = null;
        ObjectOutputStream objOut = null;
        try {
            byteOut = new ByteArrayOutputStream();
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(source);
            objOut.flush();
        } catch (IOException e) {
            logger.error(source.getClass().getName() + " serialized error!");
            e.printStackTrace();
        } finally {
            try {
                if (objOut != null) {
                    objOut.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteOut.toByteArray();
    }

    public static Object deserialize(byte[] source) {
        ObjectInputStream ObjIn = null;
        Object retVal = null;
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(source);
            ObjIn = new ObjectInputStream(byteIn);
            retVal = ObjIn.readObject();
        }
        catch (Exception e) {
            logger.error("deserialized error  !", e);
        }
        finally {
            try {
                if(null != ObjIn) {
                    ObjIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return retVal;
    }

    public static void main(String[] args) {

    }


}
