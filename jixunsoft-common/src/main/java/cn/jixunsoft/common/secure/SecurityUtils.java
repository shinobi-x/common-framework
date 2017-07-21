package cn.jixunsoft.common.secure;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * 安全管理工具类
 * 
 * @author Danfo Yam
 *
 */
public class SecurityUtils {

    /**
     * 生成MD5后的密码
     * 
     * @param password 用户输入密码
     * 
     * @return 生成后的密码
     */
    public static String generatePassword(String password) {
        return MD5Util.getMD5String(password);
    }
    
    /**
     * 客户端加密信息反解。
     * 
     * @param security
     * @return
     */
    public static String securityDecode(String security) {
        if (StringUtils.isBlank(security)) {
            return "";
        }
        
        byte[] bytes = Base64.decodeBase64(security);

        try {
            byte[] decodedBytes = M9.m9_decode(bytes);
            return new String(decodedBytes);
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * 服务端输出加密。
     * 
     * @param security
     * @return
     */
    public static String securityEncode(String security) {
        if (StringUtils.isBlank(security)) {
            return "";
        }
        
        byte[] data = security.getBytes();
        byte[] encodedBytes = M9.m9_encode(data);

        return Base64.encodeBase64String(encodedBytes);
    }

    public static void main(String[] args) {
        String s = "abc";
        String security = securityEncode(s);
        String result = securityDecode(security);
        System.out.println(result);
    }
}
