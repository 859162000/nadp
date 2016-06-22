package cn.com.netease.nadp.common.center;

import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import cn.com.netease.nadp.common.vo.ConfigVO;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cn.com.netease.nadp.common.center
 * Created by bjbianlanzhou on 2016/6/21.
 * Description
 */
public final class ConfigCenter {
    private static final List<ConfigVO> config = new ArrayList<ConfigVO>();

    protected static void config(CuratorFramework curatorFramework){
        try {
            loadConfig(curatorFramework);
            if(curatorFramework.checkExists().forPath(Constants.CONFIG_CENTER)==null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constants.CONFIG_CENTER);
            }else{
                curatorFramework.getData().usingWatcher(new ConfigWatcher(curatorFramework)).forPath(Constants.CONFIG_CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static class ConfigWatcher implements  Watcher{
        private CuratorFramework curatorFramework;
        ConfigWatcher(CuratorFramework curatorFramework){
            this.curatorFramework = curatorFramework;
        }
        public void process(WatchedEvent event) {
            if(EventType.NodeDataChanged == event.getType()){
                loadConfig(curatorFramework);
            }
        }
    }
    private static void loadConfig(CuratorFramework curatorFramework){
        try {
            config.clear();
            byte[] data = curatorFramework.getData().forPath(Constants.CONFIG_CENTER);
            if(data != null){
                config.addAll((List<ConfigVO>)SerializeUtils.unserialize(data));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
