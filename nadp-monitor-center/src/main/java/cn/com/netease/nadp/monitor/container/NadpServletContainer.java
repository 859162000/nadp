package cn.com.netease.nadp.monitor.container;

import cn.com.netease.nadp.monitor.utils.SpringUtils;
import cn.com.netease.nadp.monitor.annotations.NadpResource;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * cn.com.netease.nadp.web.monitor.container
 * Created by bjbianlanzhou on 2016/6/17.
 * Description
 */
public class NadpServletContainer extends ResourceConfig {
    /**
     * Register JAX-RS application components.
     */
    public NadpServletContainer() {
        Map<String,Object> map = SpringUtils.getBeanByAnnotation(NadpResource.class);
        Set<Entry<String, Object>> set = map.entrySet();
        for (Entry<String, Object> aSet : set) {
            register(aSet.getValue().getClass());
        }
    }
}
