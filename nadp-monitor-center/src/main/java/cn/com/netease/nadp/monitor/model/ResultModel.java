package cn.com.netease.nadp.monitor.model;

import cn.com.netease.nadp.monitor.common.Constant;

import java.util.Map;

/**
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class ResultModel {
    private String code;
    private Constant.ResultMessage resultMessage;
    private Object info;

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Constant.ResultMessage getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(Constant.ResultMessage resultMessage) {
        this.resultMessage = resultMessage;
    }



    public void setDefault(Constant.ResultCode code){
        if(code == Constant.ResultCode.SUCCESS){
            setCode(Constant.ResultCode.SUCCESS.getCode());
            setResultMessage(Constant.ResultMessage.SUCCESS);
        }else if(code == Constant.ResultCode.FAIL){
            setCode(Constant.ResultCode.FAIL.getCode());
            setResultMessage(Constant.ResultMessage.FAIL);
        }else{
            //do something
        }
    }
}
