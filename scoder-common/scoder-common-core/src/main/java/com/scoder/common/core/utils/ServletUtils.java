package com.scoder.common.core.utils;

import com.scoder.common.core.constant.Constants;
import com.scoder.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for handling HTTP servlet-related operations.
 * <p>
 * Provides methods to access HTTP request, response, session objects,
 * as well as utility methods for parameter extraction, rendering responses,
 * and encoding/decoding content.
 *
 * @author Shawn Cui
 */
public class ServletUtils {

    /**
     * Retrieves a String parameter from the HTTP request.
     *
     * @param name the name of the parameter
     * @return the parameter value as a String, or null if not found
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * Retrieves a String parameter from the HTTP request, with a default value.
     *
     * @param name         the name of the parameter
     * @param defaultValue the default value if the parameter is not found
     * @return the parameter value as a String, or the default value if not found
     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Retrieves an Integer parameter from the HTTP request.
     *
     * @param name the name of the parameter
     * @return the parameter value as an Integer, or null if not found
     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * Retrieves an Integer parameter from the HTTP request, with a default value.
     *
     * @param name         the name of the parameter
     * @param defaultValue the default value if the parameter is not found
     * @return the parameter value as an Integer, or the default value if not found
     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * Retrieves the current HTTP request object.
     *
     * @return the current HttpServletRequest, or null if not available
     */
    public static HttpServletRequest getRequest() {
        try {
            return getRequestAttributes().getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves the current HTTP response object.
     *
     * @return the current HttpServletResponse, or null if not available
     */
    public static HttpServletResponse getResponse() {
        try {
            return getRequestAttributes().getResponse();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves the current HTTP session object.
     *
     * @return the current HttpSession, or null if not available
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * Retrieves the current ServletRequestAttributes object.
     *
     * @return the current ServletRequestAttributes, or null if not available
     */
    public static ServletRequestAttributes getRequestAttributes() {
        try {
            RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
            return (ServletRequestAttributes) attributes;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves all HTTP headers from the request.
     *
     * @param request the HttpServletRequest object
     * @return a map of header names and their corresponding values
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, value);
            }
        }
        return map;
    }

    /**
     * Renders a string to the HTTP response.
     *
     * @param response the HttpServletResponse object
     * @param string   the content to render
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if the request is an AJAX request.
     *
     * @param request the HttpServletRequest object
     * @return true if the request is an AJAX request, false otherwise
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.contains("application/json")) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest")) {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
            return true;
        }
        return false;
    }

    /**
     * Encodes a string into application/x-www-form-urlencoded format.
     *
     * @param str the content to encode
     * @return the encoded string, or an empty string if encoding fails
     */
    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * Decodes a string from application/x-www-form-urlencoded format.
     *
     * @param str the content to decode
     * @return the decoded string, or an empty string if decoding fails
     */
    public static String urlDecode(String str) {
        try {
            return URLDecoder.decode(str, Constants.UTF8);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
}