package cn.com.netease.nadp.common.processor;


import javax.annotation.PostConstruct;

import cn.com.netease.nadp.common.application.Application;
import cn.com.netease.nadp.common.registryCenter.RegistryCenter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by bianlanzhou on 16/6/6.
 * Description springContextListener
 */
public class ApplicationInstantiationBeanPostProcessor implements ApplicationListener<ContextRefreshedEvent>{
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        if(applicationContext==null){
            Application application = (Application)applicationContext.getBean("application");
            RegistryCenter registryCenter = (RegistryCenter)applicationContext.getBean("registryCenter");
            if(application != null && registryCenter != null){
                RegistryProcessor.doRegistry(registryCenter,application);
            }
        }
    }
}
