package cn.com.netease.nadp.configuration.register;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * XML转换BEAN的PARSE类
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class ConfigurationRegisterParser extends AbstractSimpleBeanDefinitionParser {

    private ConfigurationRegister register;

    private Class<?> classz;

    public ConfigurationRegisterParser(Class<?> classz){
        this.classz = classz;
    }

    @Override
    protected Class<?> getBeanClass(Element element) {
        return this.classz;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String host = element.getAttribute("address");
        String port = element.getAttribute("appKey");
        if(host==null||port==null){
            new RuntimeException("registry address or port is null !").printStackTrace();
            System.exit(1);
        }
        builder.addPropertyValue("address", host);
        builder.addPropertyValue("appKey", port);
    }
}
