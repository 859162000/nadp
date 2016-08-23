package cn.com.netease.nadp.monitor.service.env;

import cn.com.netease.nadp.monitor.dao.EnvDao;
import cn.com.netease.nadp.monitor.vo.EnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by bjbianlanzhou on 2016/8/10.
 * Description
 */
@Service
public class EnvServiceImpl implements EnvService {
    @Autowired
    private EnvDao dao;

    public List<EnvVO> getData(String name, String status, int pageFrom, int pageCapacity) {
        return dao.select(null,name,status,pageFrom,pageCapacity);
    }

    public List<EnvVO> getAll(String status) {
        return dao.selectAll(status);
    }

    public int getDataCount(String name, String status) {
        return dao.selectDataCount(name, status);
    }

    public int insert(String name, String description,String zkAddress) {
        return dao.insert(name, description,zkAddress);
    }
}
