package com.scoder.common.core.utils.ip;

import com.scoder.common.core.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Utility class for handling IP-related operations.
 * <p>
 * This class provides methods to extract client IP addresses from HTTP requests
 * and validate whether an IP address belongs to internal or private networks.
 * It also includes methods to retrieve host IP and host name.
 *
 * @author Shawn Cui
 */
public class IpUtils {

    /**
     * Retrieves the client IP address from the HTTP request.
     * <p>
     * This method checks several headers for the IP address as it might pass through multiple proxies.
     * If no valid IP is found, the remote address from the request is used as a fallback.
     *
     * @param request the HttpServletRequest object
     * @return the client IP address as a String
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String ip = null;
        String ipAddresses = request.getHeader("X-Forwarded-For");

        if (StringUtils.isEmpty(ipAddresses) || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ipAddresses) || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("X-Real-IP");
        }

        // If multiple IPs are present, the first one is usually the client's real IP.
        if (!StringUtils.isEmpty(ipAddresses)) {
            ip = ipAddresses.split(",")[0];
        }

        // Fallback to the remote address if no IP is found
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // Convert IPv6 localhost to IPv4 localhost
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * Checks if the given IP address belongs to an internal/private network.
     *
     * @param ip the IP address to check
     * @return true if the IP is internal, false otherwise
     */
    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    /**
     * Helper method to validate if the given byte array represents an internal IP.
     *
     * @param addr the byte array representation of an IP address
     * @return true if the IP is internal, false otherwise
     */
    private static boolean internalIp(byte[] addr) {
        if (StringUtils.isNull(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];

        // Define internal IP ranges
        final byte SECTION_1 = 0x0A; // 10.x.x.x
        final byte SECTION_2 = (byte) 0xAC; // 172.16.x.x - 172.31.x.x
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        final byte SECTION_5 = (byte) 0xC0; // 192.168.x.x
        final byte SECTION_6 = (byte) 0xA8;

        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
                break;
            case SECTION_5:
                if (b1 == SECTION_6) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    /**
     * Converts an IPv4 address from string to byte array format.
     *
     * @param text the IPv4 address as a string
     * @return the byte array representation of the IP address, or null if the format is invalid
     */
    public static byte[] textToNumericFormatV4(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            switch (elements.length) {
                case 1:
                    long l = Long.parseLong(elements[0]);
                    if (l < 0L || l > 4294967295L) {
                        return null;
                    }
                    bytes[0] = (byte) (l >> 24 & 0xFF);
                    bytes[1] = (byte) ((l >> 16) & 0xFF);
                    bytes[2] = (byte) ((l >> 8) & 0xFF);
                    bytes[3] = (byte) (l & 0xFF);
                    break;
                case 4:
                    for (int i = 0; i < 4; i++) {
                        int value = Integer.parseInt(elements[i]);
                        if (value < 0 || value > 255) {
                            return null;
                        }
                        bytes[i] = (byte) value;
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

    /**
     * Retrieves the host's IP address.
     *
     * @return the host IP address as a string, or "127.0.0.1" if it cannot be determined
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    /**
     * Retrieves the host's name.
     *
     * @return the host name as a string, or "Unknown" if it cannot be determined
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown";
        }
    }
}