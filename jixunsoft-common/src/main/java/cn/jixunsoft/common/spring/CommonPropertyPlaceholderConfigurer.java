package cn.jixunsoft.common.spring;

import java.util.HashMap;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class CommonPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {

        super.processProperties(beanFactoryToProcess, props);

        if (PropertiesUtils.getMap() == null) {
            PropertiesUtils.setMap(new HashMap<String, String>());
        }

        for (Object key : props.keySet()) { 
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            PropertiesUtils.getMap().put(keyStr, value);
        }
    }
}
