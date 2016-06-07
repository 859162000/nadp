package cn.com.netease.nadp.common.nameSpace;

import cn.com.netease.nadp.common.application.ApplicationParser;
import cn.com.netease.nadp.common.registryCenter.RegistryCenterParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class NadpNameSpaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("application",new ApplicationParser());
        registerBeanDefinitionParser("registryCenter",new RegistryCenterParser());
    }
}
