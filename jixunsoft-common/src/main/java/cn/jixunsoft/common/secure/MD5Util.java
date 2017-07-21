package cn.jixunsoft.common.secure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 生成MD5码以及校验
 * 
 * @author Danfo Yam
 * 
 * @date 2013年9月9日
 */
public class MD5Util {

    /**
     * 生成字符串的md5校验值
     * 
     * @param s
     * @return
     */
    public static String getMD5String(String str) {
        if (null == str || "".equals(str)) {
            return null;
        }

        return DigestUtils.md5Hex(str);
    }

    /**
     * 判断md5校验码匹配
     * 
     * @param password
     *            要校验的字符串
     * @param md5PwdStr
     *            已知的md5校验码
     * @return
     */
    public static boolean checkMD5(String md5Stra, String md5Strb) {
        String s = getMD5String(md5Stra);
        return StringUtils.equals(s, md5Strb);
    }

    /**
     * 生成文件的md5校验值
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static String getFileMD5String(File file) {
        FileInputStream inStream = null;
        try {
            inStream = new FileInputStream(file);
            return DigestUtils.md5Hex(inStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(null != inStream) {
                try {
                    inStream.close();
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.getMD5String("111111"));
    }
}
