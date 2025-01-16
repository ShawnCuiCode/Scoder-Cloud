package com.scoder.common.core.utils.http;

import com.scoder.common.core.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;

/**
 * Utility class for HTTP operations.
 * <p>
 * This class provides methods to send HTTP and HTTPS requests, including GET and POST requests.
 * It also supports SSL connections by bypassing SSL certificate validation for development purposes.
 *
 * @author Shawn Cui
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * Sends an HTTP GET request to the specified URL with the given parameters.
     *
     * @param url   the target URL
     * @param param the query parameters in the format "key1=value1&key2=value2"
     * @return the response from the remote server as a String
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, Constants.UTF8);
    }

    /**
     * Sends an HTTP GET request to the specified URL with the given parameters and character encoding.
     *
     * @param url         the target URL
     * @param param       the query parameters in the format "key1=value1&key2=value2"
     * @param contentType the character encoding for the response
     * @return the response from the remote server as a String
     */
    public static String sendGet(String url, String param, String contentType) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();

            // Set HTTP headers
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

            // Establish the connection
            connection.connect();

            // Read the response
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), contentType));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("HttpUtils.sendGet ConnectException: url={}, param={}", url, param, e);
        } catch (SocketTimeoutException e) {
            log.error("HttpUtils.sendGet SocketTimeoutException: url={}, param={}", url, param, e);
        } catch (IOException e) {
            log.error("HttpUtils.sendGet IOException: url={}, param={}", url, param, e);
        } catch (Exception e) {
            log.error("HttpUtils.sendGet Exception: url={}, param={}", url, param, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("Error closing BufferedReader: url={}, param={}", url, param, ex);
            }
        }
        return result.toString();
    }

    /**
     * Sends an HTTP POST request to the specified URL with the given parameters.
     *
     * @param url   the target URL
     * @param param the body parameters in the format "key1=value1&key2=value2"
     * @return the response from the remote server as a String
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            log.info("sendPost - {}", url);
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();

            // Set HTTP headers
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");

            // Enable output and input
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Write the request body
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();

            // Read the response
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
        } catch (ConnectException e) {
            log.error("HttpUtils.sendPost ConnectException: url={}, param={}", url, param, e);
        } catch (SocketTimeoutException e) {
            log.error("HttpUtils.sendPost SocketTimeoutException: url={}, param={}", url, param, e);
        } catch (IOException e) {
            log.error("HttpUtils.sendPost IOException: url={}, param={}", url, param, e);
        } catch (Exception e) {
            log.error("HttpUtils.sendPost Exception: url={}, param={}", url, param, e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                log.error("Error closing streams: url={}, param={}", url, param, ex);
            }
        }
        return result.toString();
    }

    /**
     * Sends an HTTPS POST request to the specified URL with the given parameters.
     * <p>
     * This method bypasses SSL certificate validation, making it suitable for development environments.
     *
     * @param url   the target URL
     * @param param the body parameters in the format "key1=value1&key2=value2"
     * @return the response from the remote server as a String
     */
    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        try {
            log.info("sendSSLPost - {}", url);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

            URL console = new URL(url);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();

            // Set HTTP headers
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");

            // Configure SSL
            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());

            // Connect and read response
            conn.connect();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
            }
            log.info("recv - {}", result);
        } catch (Exception e) {
            log.error("HttpUtils.sendSSLPost Exception: url={}, param={}", url, param, e);
        }
        return result.toString();
    }

    /**
     * Custom TrustManager that trusts all certificates.
     */
    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    /**
     * Custom HostnameVerifier that allows all hostnames.
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}