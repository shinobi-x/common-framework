package cn.jixunsoft.common.oss;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.PutObjectResult;

public class AliOSS {

    private OSSClient client;

    private String bucketName;

    public AliOSS(String accessID, String accessKey, String bucketName, String endPoint) {
        this.bucketName = bucketName;
        client = new OSSClient(endPoint, accessID, accessKey);
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite); 
    }

    /**
     * 上传图片文件
     * 
     * @param fileName
     * @param file
     */
    public void uploadFile(String fileName, File file) {
        try {
            client.putObject(this.bucketName, fileName, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 上传图片字节流
     * 
     * @param fileName
     * @param fileInputStream
     */
    public void uploadFileInputStream(String fileName, InputStream inputStream) {
        try {
            client.putObject(this.bucketName, fileName, inputStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
