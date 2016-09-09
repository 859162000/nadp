package cn.com.netease.nadp.monitor.service.auth;

import cn.com.netease.nadp.monitor.common.Constant;
import cn.com.netease.nadp.monitor.dao.AuthDao;
import cn.com.netease.nadp.monitor.utils.MD5Utils;
import cn.com.netease.nadp.monitor.vo.AuthVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/9/2.
 */
@Service("authService")
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthDao dao;
    public boolean auth(AuthVO vo){
        vo.setStatus(Constant.STATUS_USEFUL);
        try {
            if(null == vo.getPassword() || "".equals(vo.getPassword())){
                return false;
            }
            vo.setPassword(MD5Utils.Bit16(vo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<AuthVO> authVOList = dao.select(vo);
        if(authVOList==null||authVOList.size()<=0){
            return false;
        }
        return true;
    }
}
