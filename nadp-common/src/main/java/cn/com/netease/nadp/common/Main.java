package cn.com.netease.nadp.common;

import cn.com.netease.nadp.common.center.RegistryCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bianlanzhou on 16/6/8.
 * Description
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String args[]){

        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                if (context != null) {
                    if(((RegistryCenter)context.getBean("registry"))!=null){
                        ((RegistryCenter)context.getBean("registry")).destory();
                    }
                    context.stop();
                    context.close();
                }
                log.info("system has been stopped !");
            }
        }));
    }
}
