package cn.com.netease.nadp.common.annotations;

/**
 * Created by bjbianlanzhou on 2016/6/15.
 * Description
 */
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface JerseyResource {
    String value() default "";
}
