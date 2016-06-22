package cn.com.netease.nadp.common.nameSpace;

import cn.com.netease.nadp.common.application.Application;
import cn.com.netease.nadp.common.common.NadpBeanDefinitionParser;
import cn.com.netease.nadp.common.center.RegistryCenter;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by bianlanzhou on 16/6/3.
 * Description
 */
public class NadpNameSpaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("application",new NadpBeanDefinitionParser(Application.class));
        registerBeanDefinitionParser("registry",new NadpBeanDefinitionParser(RegistryCenter.class));
    }
}
