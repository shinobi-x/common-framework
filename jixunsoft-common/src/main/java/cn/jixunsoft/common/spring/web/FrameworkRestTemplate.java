package cn.jixunsoft.common.spring.web;

import java.net.URI;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class FrameworkRestTemplate extends RestTemplate {

    @Override
    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
        return super.getForObject(url, responseType);
    }

}
