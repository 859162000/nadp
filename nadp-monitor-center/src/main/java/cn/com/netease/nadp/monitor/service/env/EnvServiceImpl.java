package cn.com.netease.nadp.monitor.service.env;

import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.dao.EnvDao;
import cn.com.netease.nadp.monitor.utils.StringUtils;
import cn.com.netease.nadp.monitor.vo.EnvVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    @Transactional(propagation = Propagation.REQUIRED,isolation= Isolation.DEFAULT)
    public void update(Map<String,String> map){
        int id ;
        try{
            id = Integer.valueOf(map.get("id"));
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        EnvVO vo = new EnvVO();
        vo.setId(id);
        vo.setName(map.get("name"));
        vo.setZkAddress(map.get("zkAddress"));
        vo.setDescription(map.get("description"));
        dao.updateById(vo);
    };

    public void delete(Map<String,String> map){
        int id ;
        try{
            id = Integer.valueOf(map.get("id"));
        }catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        EnvVO vo = new EnvVO();
        vo.setId(id);
        vo.setStatus(Constant.STATUS_UNUSEFUL);
        dao.updateById(vo);
    }
}
