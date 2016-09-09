package cn.com.netease.nadp.common;

/**
 * Created by bjbianlanzhou on 2016/8/2.
 * Description
 */
public class Constants {
    public static final String CONFIGURATION = "configuration";
    public static final String NADP = "nadp";
    public static final String CENTER = "center";
    public static final String DATA =  "data";
    public static final String UTF8 = "utf-8";
    public static final String URL_CENTER = "center/registry";
    public static final String CENTER_MEMERY ="center/getConfig";
    public static final String CENTER_PERSISTENCE ="center/getPersistenceConfig";

    public enum Result{
        SUCCESS("10000"),FAIL("10001");
        private String code;
        private Result(String code){
            this.code = code;
        };

        public String getCode() {
            return code;
        }
    }

    public enum Status{
        TRUE("1"),FALSE("0");
        private String code;
        private Status(String code){
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public static enum ConfigurationRegisterType{
        CENTER("center"),LOCAL("local");
        private String type;
        private ConfigurationRegisterType(String type){
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }
}