package cn.jixunsoft.common.cdn;

import java.util.List;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksRequest;
import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse;
import com.aliyuncs.cdn.model.v20141111.DescribeRefreshTasksResponse.CDNTask;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheRequest;
import com.aliyuncs.cdn.model.v20141111.PushObjectCacheResponse;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesRequest;
import com.aliyuncs.cdn.model.v20141111.RefreshObjectCachesResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class AliyunCdnClient implements CdnClient {

    private IAcsClient client;
    
    public AliyunCdnClient(String regionId, String accessKeyId, String secret) {
        
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, secret);
        client = new DefaultAcsClient(profile);
    }

    @Override
    public String refreshCaches(String objectPath) throws Throwable {
        RefreshObjectCachesRequest request = new RefreshObjectCachesRequest();
        request.setObjectPath(objectPath);
        request.setObjectType("File");
        RefreshObjectCachesResponse response = client.getAcsResponse(request);
        return response.getRefreshTaskId();
    }

    @Override
    public String pushCache(String objectPath) throws Throwable {
        PushObjectCacheRequest request = new PushObjectCacheRequest();
        request.setObjectPath(objectPath);
        PushObjectCacheResponse response = client.getAcsResponse(request);
        return response.getPushTaskId();
    }
    
    
    public List<CDNTask> describeRefreshTasks(String taskId) throws Throwable {
        DescribeRefreshTasksRequest request = new DescribeRefreshTasksRequest();
        request.setTaskId(taskId);
        DescribeRefreshTasksResponse response = client.getAcsResponse(request);
        return response.getTasks();
    }

    public static void main(String[] args) throws Throwable {
        
        AliyunCdnClient client = new AliyunCdnClient("cn-beijing", "UMnnfJSHLsbnl8Bq", "47oZCSmtsngR6Nje5DdEaY3l8k0cji");
        //String taskId = client.pushCache("http://down3.appscat.cn/group1/M00/00/05/CvsCn1W_GAqjqmTfAAANVJwBLrQ077.png");
        //System.out.println(taskId);
        List<CDNTask> cdnTaskList = client.describeRefreshTasks("206180294");
        for (CDNTask cdnTask : cdnTaskList) {
            System.out.println(cdnTask.getCreationTime() + ":" + cdnTask.getObjectPath() + ":" + cdnTask.getStatus() + ":" + cdnTask.getTaskId());
        }
    }

}
