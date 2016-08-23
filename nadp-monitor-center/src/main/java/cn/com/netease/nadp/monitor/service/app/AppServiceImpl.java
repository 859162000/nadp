package cn.com.netease.nadp.monitor.service.app;

import cn.com.netease.nadp.monitor.dao.AppDao;
import cn.com.netease.nadp.monitor.dao.AppEnvRelDao;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.AppVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by bjbianlanzhou on 2016/8/11.
 * Description
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private AppDao dao;
    @Autowired
    private AppEnvRelDao relDao;

    public List<AppVO> getData(String name, String status, int pageFrom, int PageCapacity) {
        return dao.select(name, null ,status, pageFrom, PageCapacity);
    }

    public int getDataCount(String name, String status) {
        return dao.selectDataCount(name, status);
    }

    @Transactional (propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void insert(Map<String,Object> map) {
        AppVO vo = new AppVO();
        vo.setName(StringUtils.nullObj2String(map.get("name")));
        vo.setAppKey(StringUtils.nullObj2String(map.get("appKey")));
        vo.setDescription(StringUtils.nullObj2String(map.get("description").toString()));
        dao.insert(vo);
        if(map.get("env")!=null){
            List<String> envArr = (ArrayList<String>) map.get("env");
            for(String env : envArr){
                relDao.insert(vo.getId()+"",env);
            }
        }
    }

    public List<AppVO> getAll(String status) {
        return dao.selectAll(status);
    }

}
