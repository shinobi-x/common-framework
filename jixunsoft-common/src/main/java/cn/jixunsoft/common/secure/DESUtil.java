package cn.jixunsoft.common.secure;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * DES加解密工具类
 * 
 * @author zhuww
 * 
 * @date 2014年10月21日
 */
public class DESUtil {
    
    private static final String DES_ALGORITHM = "DES";
    
    /**
     * 随机生成Des Key
     * 
     * @return deskey
     */
    public static SecretKey generateDesKey() {
        
        try {
            KeyGenerator kg = KeyGenerator.getInstance(DES_ALGORITHM);
            //kg.init(new SecureRandom(seed.getBytes()));
            return kg.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * DES算法加密
     * 
     * @param input 明文
     * @param desKey des秘钥
     * 
     * @return 密文
     */
    public static byte[] encrypt(byte[] input, Key desKey) {
        
        try {
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * DES算法解密
     * 
     * @param input 密文
     * @param desKey des秘钥
     * 
     * @return 明文
     */
    public static byte[] decrypt(byte[] input, Key desKey) {

        try {
            Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //SecretKey desKey = DESUtil.generateDesKey("37d5aed075525d4fa0fe635231cba447");
        SecretKey desKey = DESUtil.generateDesKey();
        System.out.println("key=" + new String(desKey.getEncoded()));
        String source = "37d5aed075525d4fa0fe635231cba447";
        System.out.println(source);
        byte[] encrypt = DESUtil.encrypt(source.getBytes(), desKey);
        System.out.println(new String(encrypt));
        byte[] decrypt = DESUtil.decrypt(encrypt, desKey);
        System.out.println(new String(decrypt));
    }
}
