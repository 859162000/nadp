package cn.com.netease.nadp.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by bianlanzhou on 16/6/6.
 * Description
 */
public class NetUtils {
    public static final String LOCALHOST = "127.0.0.1";

    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");

    private static final Set<String> ipSet = new HashSet<String>();

    public static Set<String> getLocalHost(){
        ipSet.addAll(getLocalAddress());
        return ipSet;
    }

    public static String getFirstRealIp(){
        if(ipSet.size()<=0){
            ipSet.addAll(getLocalHost());
        }
        for(String ip : ipSet){
            return ip;
        }
        return LOCALHOST;
    }

    public static boolean isLocalHost(String host) {
        return host != null
                && (LOCAL_IP_PATTERN.matcher(host).matches()
                || host.equalsIgnoreCase("localhost"));
    }

    public static boolean isAnyHost(String host) {
        return "0.0.0.0".equals(host);
    }

    private static boolean isValidAddress(InetAddress address) {
        if (address == null || address.isLoopbackAddress())
            return false;
        String name = address.getHostAddress();
        return (name != null
                && ! ANYHOST.equals(name)
                && ! LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    /**
     * 遍历本地网卡，返回所有合理IP。
     *
     * @return 本地网卡IP
     */
    public static Set<String> getLocalAddress() {
        Set<String> ipSet = getLocalAddress0();
        return ipSet;
    }

    private static Set<String> getLocalAddress0() {
        Set<String> ipSet = new HashSet<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        ipSet.add(address.getHostAddress()==null?"":address.getHostAddress());
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ipSet;
    }
}
