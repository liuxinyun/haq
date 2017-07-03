package com.lanwei.haq.comm.util;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.Enumeration;

/**
 * IP工具类
 *
 * @author Sun xushe
 */
public final class IpUtil {

    /**
     * 获取请求用户的ip
     *
     * @param request IP
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本机的ip
     *
     * @return 本机IP
     */
    public static String getRealIp() {

        String localIp = null, netIp = null;
        InetAddress ip;

        Enumeration<NetworkInterface> netInterfaces;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            boolean find = false;//
            while (netInterfaces.hasMoreElements() && !find) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")) {//  外网IP
                        netIp = ip.getHostAddress();
                        find = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")) {// �内网IP
                        localIp = ip.getHostAddress();
                    }
                }
            }
            //  有外网ip则返回外网ip，否则返回内网ip
            return StringUtils.isNotBlank(netIp) ? netIp : localIp;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


}
