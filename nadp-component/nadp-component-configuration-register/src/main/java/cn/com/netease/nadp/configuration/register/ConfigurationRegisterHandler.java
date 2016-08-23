package cn.com.netease.nadp.configuration.register;

import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * 配置中心命名空间Handler
 * Created by bjbianlanzhou on 2016/8/1.
 * Description
 */
public class ConfigurationRegisterHandler extends NamespaceHandlerSupport{
    public void init() {
        this.registerBeanDefinitionParser("register", new ConfigurationRegisterParser(ConfigurationRegister.class));
    }
}
