package cn.jixunsoft.cache.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;

/**
 * 序列化工具
 * 
 * @author Danfo Yam
 *
 * 2013年12月27日
 *
 */
public class SerializeUtil {

    /**
     * 序列化对象
     * 
     * @param object 待序列化对象
     * 
     * @return 序列化的字节数组
     */
    public static byte[] serialize(Object object) {
        try {
            // 序列化
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();

            return bytes;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反序列化成对象
     * 
     * @param bytes 待反序列化字节数组
     * 
     * @return 对象
     */
    public static Object deserialize(byte[] bytes) {

        try {

            if (bytes == null) {
                return null;
            }
            // 转换成play 的classloader
            return new ObjectInputStream(new ByteArrayInputStream(bytes)) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
                    return Class.forName(desc.getName());
                }
            }.readObject();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
