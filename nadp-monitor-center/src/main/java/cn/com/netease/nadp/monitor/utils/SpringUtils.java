package cn.com.netease.nadp.monitor.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * SPRING BEAN 容器
 * Created by bjbianlanzhou on 2016/6/15.
 * Description
 */

public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtils.applicationContext = applicationContext;
    }

    /**
     * 通过注解获取类
     * @param z
     * @return
     */
    public static Map<String,Object> getBeanByAnnotation(Class<? extends Annotation> z){
        return applicationContext.getBeansWithAnnotation(z);
    }

    /**
     * 通过名称获取类
     * @param name
     * @return
     */
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }

    /**
     * 通过类型获取类
     * @param classz
     * @param <T>
     * @return
     */
    public static <T>Map<String,T> getBeanByType(Class<T> classz){
        return applicationContext.getBeansOfType(classz);
    }



}
