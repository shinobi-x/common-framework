package cn.jixunsoft.common.secure;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AesUtil {
    
    /** 
     * 加密 
     *  
     * @param content   需要加密的内容 
     * @param password  加密密码 
     * @return 
     */   
    public static byte[] encrypt(String content, String password) {
        
        try {           
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");   
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器   
            byte[] byteContent = content.getBytes("utf-8");   
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化   
            byte[] result = cipher.doFinal(byteContent);   
            return result; // 加密   
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }   
    
    /**
     * 解密 
     * 
     * @param content  待解密内容 
     * @param password 解密密钥 
     * @return 
     */   
     public static byte[] decrypt(byte[] content, String password) {
         
         try {
             SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");               
             Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器   
             cipher.init(Cipher.DECRYPT_MODE, key);// 初始化   
             byte[] result = cipher.doFinal(content);   
             return result; // 加密   
         } catch (Exception e) {
             throw new RuntimeException(e);
         } 
         
     }  
       
       
     /**
      * 将二进制转换成16进制
      * 
      * @param buf 
      * @return 
      */   
      public static String parseByte2HexStr(byte buf[]) {
          
          StringBuffer sb = new StringBuffer();   
          for (int i = 0; i < buf.length; i++) {   
              String hex = Integer.toHexString(buf[i] & 0xFF);   
              if (hex.length() == 1) {   
                  hex = '0' + hex;   
              }   
              sb.append(hex.toUpperCase());   
          }
          
          return sb.toString();   
      }   
        
      /**
       * 将16进制转换为二进制
       * 
       * @param hexStr 
       * @return 
       */   
       public static byte[] parseHexStr2Byte(String hexStr) {   
           if (hexStr.length() < 1) {
                   return null;
           }
           
           byte[] result = new byte[hexStr.length()/2];   
           for (int i = 0;i< hexStr.length()/2; i++) {   
               int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);   
               int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);   
               result[i] = (byte) (high * 16 + low);   
           }
           
           return result;   
       }   
      
       
       public static void main(String[] args) throws IOException {
           
           String password = "fO0jyYjHfn/dU4Yt";   
           
           String input = "{\"tuid\": \"303030383536333075301121173033727\",\"board\": \"CA6232-D\",\"model\": \"XXX\",\"os_ver\": \"NS4_01_00_09356_09356\",\"config_tm\": \"xxxxxxxx\",\"live_time\": 1234}";
           //String output = "{\"code\": 200,\"msg\": \"\",\"data\": {\"config\": {\"hb_interval\": 3600,\"hb_retry_interval\": 30},\"upgrade\": [{\"os_ver\": \"NS4_00_00_9300_9300\",\"rel_note\": \"版本提升了系统性能\",\"rel_time\": \"2015-12-15 15:32:12\",\"inc_url\": \"http://xxxxxx/xxxxxxxx/xxxx\",\"full_url\": \"http://xxxxxx/xxxxxxxx/yyyy\"}]}}";   
           
           aesEncryptTest(input, password);
           //aesEncryptTest(output, password);
           
           String secret = "+1M5hdTvc05zf15W8qf3uffAV2m8E1lEBxMvr1xJeC5eDsfzMfyEbCxUd6a5v/W3IshE9jn6TFkj/W6QZR8irxLzjrZpQ9yHm2TQCSa1sEvynLwLpnn3Mjsn2Rv+LKP6";
           aesDecryptTest(secret, password);
     }
     
     public static void aesEncryptTest(String content, String password) throws IOException {
         //加密
         System.out.println("加密前：" + content);
         //FileUtils.writeStringToFile(new File("d:\\source.txt"), content, "UTF-8");
         String encryptResult = new String(Base64.encodeBase64(encrypt(content, password)));  
         System.out.println("加密后：" + encryptResult);
         //FileUtils.writeStringToFile(new File("d:\\secret.txt"), encryptResult, "UTF-8");
         
     }
     
     public static void aesDecryptTest(String secret, String password) {
         //解密
         System.out.println("解密前：" + secret);
         String decryptResult = new String(decrypt(Base64.decodeBase64(secret),password));   
         System.out.println("解密后：" + decryptResult);
     }
}
