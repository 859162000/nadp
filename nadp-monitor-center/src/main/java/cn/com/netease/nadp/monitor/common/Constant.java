package cn.com.netease.nadp.monitor.common;

/**
 * cn.com.netease.nadp.monitor.common
 * Created by bjbianlanzhou on 2016/6/20.
 * Description
 */
public class Constant {
    public static final int PAGINATION_MAX_COUNT=10;

    public static final String CONFIGURATION_STATUS = "0";

    public static final String PATH_ROOT = "/configuration";

    public static final String PATH_DATA = "/data";



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

    public static enum ConfigurationType{
        PERSISTENCE("持久型","1"),MEMORY("内存型","2");
        private String name;
        private String code;
        private ConfigurationType(String name,String code){
            this.name = name;
            this.code = code;
        }
        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
