package cn.com.netease.nadp.configuration.register;

/**
 * Created by bjbianlanzhou on 2016/8/12.
 * Description
 */
public class RegistException extends RuntimeException {
    private static String RECONNECT_EXCEPTION = "regist fail .";
    public RegistException(String detail){
        super(RECONNECT_EXCEPTION + detail);
    }
}
