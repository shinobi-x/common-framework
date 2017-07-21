package cn.jixunsoft.common.secure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * RSA加解密工具类
 * 
 * @author zhuww
 * 
 * @date 2014年10月21日
 */
public class RSAUtil {

    public static final String RSA_ALGORTHM="RSA";
    
    public static final String SIGNATURE_ALGORITHM="MD5withRSA";
    
    /**
     * 从文件中读取秘钥
     * 
     * @param keyFile 秘钥文件
     * 
     * @return 秘钥
     */
    private static String loadKey(File keyFile) {
        
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        
        try {
            fis = new FileInputStream(keyFile);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            
            String readLine = null;
            StringBuilder sb = new StringBuilder();
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    /** 
     * 从文件加载私钥
     * 
     * @param privateKeyPath 私钥文件
     * 
     * @return 私钥对象 
     */  
    public static RSAPrivateKey loadPrivateKey(File privateKeyFile) {
        
        String privateKey = loadKey(privateKeyFile);
        
        try {
            // 对私钥解密
            byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
            
            // 取私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
            return (RSAPrivateKey)keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }  
    
    /** 
     * 从文件加载公钥
     * 
     * @param publicKeyPath 公钥文件
     * 
     * @return 公钥对象 
     */  
    public static RSAPublicKey loadPublicKey(File publicKeyFile) {
        
        String publicKey = loadKey(publicKeyFile);
        
        try {
            // 对公钥解密
            byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
            
            // 取公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
            return (RSAPublicKey)keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
    
    /**
     * 从私钥字符串得到私钥对象
     * 
     * @param privateKey 私钥字符串
     * 
     * @return 私钥对象
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) {
        
        try {
            // 对私钥解密
            byte[] keyBytes = new BASE64Decoder().decodeBuffer(privateKey);
            
            // 取私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
            return (RSAPrivateKey)keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 从公钥字符串得到公钥对象
     * 
     * @param publicKey 公钥字符串
     * 
     * @return 公钥对象
     */
    public static RSAPublicKey getPublicKey(String publicKey) {
        
        try {
            // 对公钥解密
            byte[] keyBytes = new BASE64Decoder().decodeBuffer(publicKey);
            
            // 取公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORTHM);
            return (RSAPublicKey)keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 用私钥加密
     * 
     * @param data 明文
     * @param privateKey 私钥
     * 
     * @return 密文
     */
    public static byte[] encryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) {
        
        try {
            // 对数据加密
            Cipher cipher = Cipher.getInstance(RSA_ALGORTHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 用私钥解密
     * 
     * @param data 密文
     * @param privateKey 私钥
     * 
     * @return 明文
     */
    public static byte[] decryptByPrivateKey(byte[] data, RSAPrivateKey privateKey) {
        
        try {
            // 对数据解密
            Cipher cipher = Cipher.getInstance(RSA_ALGORTHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 用公钥加密
     * 
     * @param data 明文
     * @param publicKey 公钥
     * 
     * @return 密文
     */
    public static byte[] encryptByPublicKey(byte[] data, RSAPublicKey publicKey) {
        
        try {
            // 对数据加密
            Cipher cipher = Cipher.getInstance(RSA_ALGORTHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 用公钥解密
     * 
     * @param data 密文
     * @param publicKey 公钥
     * 
     * @return 明文
     */
    public static byte[] decryptByPublicKey(byte[] data, RSAPublicKey publicKey) {
        
        try {
            // 对数据解密
            Cipher cipher = Cipher.getInstance(RSA_ALGORTHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 用私钥对信息生成数字签名
     * 
     * @param data 数据
     * @param privateKey 私钥
     * 
     * @return 数字签名
     */
    public static String sign(byte[] data, RSAPrivateKey privateKey) {
        
        try {
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            signature.update(data);
            
            return new BASE64Encoder().encode(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 校验数字签名
     * 
     * @param data 数据
     * @param publicKey 公钥
     * @param sign 数字签名
     * 
     * @return 校验结果
     */
    public static boolean verify(byte[] data, RSAPublicKey publicKey, String sign) {
        
        try {
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            
            // 验证签名是否正常
            return signature.verify(new BASE64Decoder().decodeBuffer(sign));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) throws IOException {
        String keyPath = "D:/WorkSpace/cf-box/box-admin/ssl/";
        System.out.println("keyPath=" + keyPath);
        System.out.println("---------------------------------------------------------------");
        //testRsaGenerator();
        System.out.println("---------------------------------------------------------------");
        //testRsaFile(keyPath);
        System.out.println("---------------------------------------------------------------");
        testRsaString();
    }
    
    public static void testRsaGenerator() throws IOException {
        
        String data = "37d5aed075525d4fa0fe635231cba447";
        System.out.println("data=" + data);
        
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA_ALGORTHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }  
        keyPairGen.initialize(1024);  
        KeyPair keyPair = keyPairGen.generateKeyPair();  
  
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  
        String rsaPublicKey = new BASE64Encoder().encode(publicKey.getEncoded());
        System.out.println("rsa Public Key=");
        System.out.println(rsaPublicKey);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String rsaPrivateKey = new BASE64Encoder().encode(privateKey.getEncoded());
        System.out.println("rsa Private Key=");
        System.out.println(rsaPrivateKey);
        
        byte[] rsaEncryptKey = RSAUtil.encryptByPublicKey(data.getBytes(), publicKey);
        System.out.println("rsa encode=" + new String(rsaEncryptKey));
        String deskey3 = new String(RSAUtil.decryptByPrivateKey(rsaEncryptKey, privateKey));
        System.out.println("rsa decode=" + deskey3);
        
        String base64Key = new BASE64Encoder().encodeBuffer(rsaEncryptKey);
        System.out.println("base64 encode=" + base64Key);
        String deskey4 = new String(RSAUtil.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(base64Key), privateKey));
        System.out.println("base64 rsa decode=" + deskey4);
    }
    
    public static void testRsaFile(String keyPath) throws IOException {
        
        String data = "37d5aed075525d4fa0fe635231cba447";
        System.out.println("data=" + data);
        
        /**
         * 生成RSA秘钥命令,nocrypt参数必须放在前面，放在后面时生成的秘钥Java无法解析
         * 
         * openssl
         * genrsa -out mm.app.cfbox.rsakey.private.pem 1024
         * rsa -in mm.app.cfbox.rsakey.private.pem -pubout -out mm.app.cfbox.rsakey.public.pem
         * pkcs8 -topk8 -nocrypt -inform PEM -in mm.app.cfbox.rsakey.private.pem -outform PEM -out mm.app.cfbox.rsakey.private_pkcs8.pem
         */
        String publicKeyFile = keyPath + "mm.app.cfbox.rsakey.public.pem";
        String privateKeyFile = keyPath + "mm.app.cfbox.rsakey.private_pkcs8.pem";
        
        RSAPublicKey publicKey = RSAUtil.loadPublicKey(new File(publicKeyFile)); 
        String rsaPublicKey = new BASE64Encoder().encode(publicKey.getEncoded());
        System.out.println("rsa Public Key=");
        System.out.println(rsaPublicKey);
        RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(new File(privateKeyFile));
        String rsaPrivateKey = new BASE64Encoder().encode(privateKey.getEncoded());
        System.out.println("rsa Private Key=");
        System.out.println(rsaPrivateKey);
        
        byte[] rsaEncryptKey = RSAUtil.encryptByPublicKey(data.getBytes(), publicKey);
        System.out.println("rsa encode=" + new String(rsaEncryptKey));
        String deskey3 = new String(RSAUtil.decryptByPrivateKey(rsaEncryptKey, privateKey));
        System.out.println("rsa decode=" + deskey3);
        
        String base64Key = new BASE64Encoder().encode(rsaEncryptKey);
        System.out.println("base64 encode=" + base64Key);
        String deskey4 = new String(RSAUtil.decryptByPrivateKey(new BASE64Decoder().decodeBuffer(base64Key), privateKey));
        System.out.println("base64 rsa decode=" + deskey4);
    }
    
    public static void testRsaString() throws IOException {
        
        String data = "37d5aed075525d4fa0fe635231cba447";
        System.out.println("data=" + data);
        
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAI08o+PB8F4WbxgnjOhpP4tNhLYnpmRrOOsIOMyU4ft/SfqbjJXGg4lypNHlNZ4QYA8Fxuy/b82Qd8GwVBSSxHXfCXdgoSRrcCdeP7m072nVOfrxRArVYKpjN5SJMv1WbPyhLH2SNSVq2nWB/Bh9pLO7oYrruKZaThyc9C+KpVa5AgMBAAECgYBk1oHQ0BvUoUHKRhqlqNdUlxEEmMSE98TYkbMz5ax+yn/ALKSwHt61GKQys+qXIuvibeZ8C6ZAZ0AJiuJNGgina/rAe77zkej5LMuFmZvP+Bzr2cLjbNGBBJy2v0ImFzjN8/uYfK0qJSP+hcfrxQl+dV/a1XSDvx7XiCTk2NPNPQJBAME/LHFb7zMnyvjiWnaxzQaokfVZIDHR2TWyM7tjPQURasnVfAiuL0vBodORrPHDvZ6BFWxtnj4sGPdvSwN/TBsCQQC7GdL4xXXn5zhsq5zUvQxvV+zQFvxNJEMaSTRg4yDLN4fFHM0tS/H6aCIDSBSKBGJgmyi2rlTr0zGHEH6JWC27AkBaHr74IpqbrawPg2gSwHpP558h3y2PbkCwtOlBM1eKnIPDmoacebbeym9Qfjg8Cz/LwoEeLsTEU8ZU5hcCi4QPAkEAguqp7Xo1ayQmHhvirBTv+4TZZi1/MvcZoO3yIY0rqCZd8PWbN4O5eI6epg8sl/pDX/eRgGnxb+Wh9TD8aVvy+wJBAKAqZyrz/Qc4/2jYQPgbzBc03wihP+cxX+tEs53VTrCybXbzIZAVLfL8+3xS7NmrE9izt1oWy5gaSoIGZHXwLxg=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNPKPjwfBeFm8YJ4zoaT+LTYS2J6ZkazjrCDjMlOH7f0n6m4yVxoOJcqTR5TWeEGAPBcbsv2/NkHfBsFQUksR13wl3YKEka3AnXj+5tO9p1Tn68UQK1WCqYzeUiTL9Vmz8oSx9kjUlatp1gfwYfaSzu6GK67imWk4cnPQviqVWuQIDAQAB";
        
        RSAPrivateKey rsaPrivateKey = RSAUtil.getPrivateKey(privateKey);
        RSAPublicKey rsaPublicKey = RSAUtil.getPublicKey(publicKey);
        
        byte[] rsaEncryptKey = RSAUtil.encryptByPrivateKey(data.getBytes(), rsaPrivateKey);
        System.out.println("rsa encode=" + new String(rsaEncryptKey));
        String deskey3 = new String(RSAUtil.decryptByPublicKey(rsaEncryptKey, rsaPublicKey));
        System.out.println("rsa decode=" + deskey3);
        
        String base64Key = new BASE64Encoder().encode(rsaEncryptKey);
        System.out.println("base64 encode=" + base64Key);
        String deskey4 = new String(RSAUtil.decryptByPublicKey(new BASE64Decoder().decodeBuffer(base64Key), rsaPublicKey));
        System.out.println("base64 rsa decode=" + deskey4);
    }
    
}
