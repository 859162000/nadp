package cn.com.netease.nadp.common.common;

import cn.com.netease.nadp.common.application.Application;
import cn.com.netease.nadp.common.center.RegistryCenter;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by bianlanzhou on 16/6/8.
 * Description
 */
public class NadpBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
    private final Class<?> clazz;
    private static Application application;
    private static RegistryCenter registryCenter;
    public NadpBeanDefinitionParser(Class<?> clazz){
        this.clazz = clazz;
    }
    @Override
    protected Class<?> getBeanClass(Element element) {
        return this.clazz;
    }
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        if(RegistryCenter.class.equals(clazz)){
            doRegistryParse(element,parserContext,builder);
        }else if(Application.class.equals(clazz)){
            doApplicationParse(element,parserContext,builder);
        }else{
            //
        }
    }



    private void doRegistryParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String address = element.getAttribute("address");
        String port = element.getAttribute("port");
        if(address==null||port==null){
            new RuntimeException("registry address or port is null !").printStackTrace();
            System.exit(1);
        }
        builder.addPropertyValue("address", address);
        builder.addPropertyValue("port", port);
    }

    private void doApplicationParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");   //app_name
        String type = element.getAttribute("type");
        type = Constants.ApplicationType.valueOf(type).getType();
        if(type==null){
            new RuntimeException("can not find application type !").printStackTrace();
            System.exit(1);
        }
        builder.addPropertyValue("name", name);
        builder.addPropertyValue("type", type);
    }
}
