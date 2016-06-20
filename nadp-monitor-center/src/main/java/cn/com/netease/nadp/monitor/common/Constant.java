package cn.com.netease.nadp.monitor.common;

/**
 * cn.com.netease.nadp.monitor.common
 * Created by bjbianlanzhou on 2016/6/20.
 * Description
 */
public class Constant {
    public static enum ResultMessage{
        SUCCESS("success"),FAIL("fail");
        private String resultMessage;
        private ResultMessage(String result){
            this.resultMessage = resultMessage;
        }
        public String getResultMessage(){
            return resultMessage;
        }
    }
    public static enum ResultCode{
        SUCCESS("10000"),FAIL("10001");
        private String code;
        private ResultCode(String code){this.code = code;}
        public String getCode(){
            return code;
        }
    }
}
