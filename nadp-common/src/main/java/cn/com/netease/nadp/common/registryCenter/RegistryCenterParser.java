package cn.com.netease.nadp.common.registryCenter;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by bianlanzhou on 16/6/6.
 * Description
 */
public class RegistryCenterParser extends AbstractSimpleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return RegistryCenter.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String address = element.getAttribute("address");   //zk address
        String port = element.getAttribute("port");        //zk port
        if(address==null||port==null||"".equals(address)||"".equals(port)){
            new RuntimeException("can not initial zookeeper ,address or port is not exeist ! ").printStackTrace();
            System.exit(1);
        }
        builder.addPropertyValue("address", address);
        try {
            builder.addPropertyValue("port", Integer.valueOf(port));
        }catch (NumberFormatException ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
