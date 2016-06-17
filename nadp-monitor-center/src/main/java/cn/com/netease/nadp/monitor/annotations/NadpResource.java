package cn.com.netease.nadp.monitor.annotations;

/**
 * RESOURCE的注解
 * Created by bjbianlanzhou on 2016/6/15.
 * Description
 */

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface NadpResource {
    String value() default "";
}
