package cn.com.netease.nadp.common.center;

import cn.com.netease.nadp.common.common.Constants;
import cn.com.netease.nadp.common.utils.SerializeUtils;
import cn.com.netease.nadp.common.vo.ConfigVO;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;

import javax.xml.soap.Node;
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

    protected static void config(final CuratorFramework curatorFramework){
        NodeCache nodeCache = null;
        try {
            if(curatorFramework.checkExists().forPath(Constants.CONFIG_CENTER)==null){
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(Constants.CONFIG_CENTER);
            }
            nodeCache = new NodeCache(curatorFramework,Constants.CONFIG_CENTER);
            nodeCache.start();
            nodeCache.getListenable().addListener(new NodeCacheListener() {

                public void nodeChanged() throws Exception {
                    loadConfig(curatorFramework);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



//    protected static class ConfigWatcher implements Watcher{
//        private CuratorFramework curatorFramework;
//        ConfigWatcher(CuratorFramework curatorFramework){
//            this.curatorFramework = curatorFramework;
//        }
//        public void process(WatchedEvent event) {
//            System.out.println(event.getType());
//            if(EventType.NodeDataChanged == event.getType()){
//                loadConfig(curatorFramework);
//            }
//        }
//    }
    private static void loadConfig(CuratorFramework curatorFramework){
        try {
            config.clear();
            byte[] data = curatorFramework.getData().forPath(Constants.CONFIG_CENTER);
            if(data != null){
                config.addAll((List<ConfigVO>)SerializeUtils.unserialize(data));
                for(int i =0 ;i<config.size();i++){
                    System.out.println(config.get(i).getKey()+":"+config.get(i).getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
