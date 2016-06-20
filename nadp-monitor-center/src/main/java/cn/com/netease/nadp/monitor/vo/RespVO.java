package cn.com.netease.nadp.monitor.vo;

import cn.com.netease.nadp.monitor.common.Constant;

import java.util.Map;

/**
 * cn.com.netease.nadp.monitor.vo
 * Created by bjbianlanzhou on 2016/6/20.
 * Description
 */
public class RespVO {

    private Constant.ResultCode code;
    private Constant.ResultMessage resultMessage;
    private Map<String,Object> data;

    public Constant.ResultCode getCode() {
        return code;
    }

    public void setCode(Constant.ResultCode code) {
        this.code = code;
    }

    public Constant.ResultMessage getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Constant.ResultMessage resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setDefault(Constant.ResultCode code){
        if(code == Constant.ResultCode.SUCCESS){
            setCode(Constant.ResultCode.SUCCESS);
            setResultMessage(Constant.ResultMessage.SUCCESS);
        }else if(code == Constant.ResultCode.FAIL){
            setCode(Constant.ResultCode.FAIL);
            setResultMessage(Constant.ResultMessage.FAIL);
        }else{
            //do something
        }
    }
}
