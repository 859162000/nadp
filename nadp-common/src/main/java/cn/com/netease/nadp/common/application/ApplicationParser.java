package cn.com.netease.nadp.common.application;

import cn.com.netease.nadp.common.common.Constants;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class ApplicationParser extends AbstractSimpleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return Application.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
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
