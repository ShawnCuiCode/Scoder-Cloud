package com.scoder.common.security.service;

import com.scoder.common.core.constant.CacheConstants;
import com.scoder.common.core.constant.Constants;
import com.scoder.common.core.utils.IdUtils;
import com.scoder.common.core.utils.SecurityUtils;
import com.scoder.common.core.utils.ServletUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.redis.service.RedisService;
import com.scoder.user.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Token Service for managing user authentication tokens.
 *
 * @author Shawn Cui
 */
@Component
public class TokenService {
    private static final long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60 * 60;
    private static final String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;
    private static final long MILLIS_SECOND = 1000;
    private static final long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;
    @Autowired
    private RedisService redisService;

    /**
     * Creates a new token for the given user and stores it in Redis.
     *
     * @param loginUser The user for whom the token is being created.
     * @return A map containing the token and expiration time.
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        refreshToken(loginUser);

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", token);
        response.put("expires_in", EXPIRE_TIME);
        redisService.setCacheObject(getTokenKey(token), loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
        return response;
    }

    /**
     * Retrieves the current logged-in user's information from the request token.
     *
     * @return The LoginUser object, or null if the token is invalid.
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * Stores the updated user information in Redis and refreshes the token expiration.
     *
     * @param loginUser The user whose token is being updated.
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * Retrieves the user's information based on the token in the given request.
     *
     * @param request The HTTP request containing the token.
     * @return The LoginUser object, or null if the token is invalid.
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            return redisService.getCacheObject(userKey);
        }
        return null;
    }

    /**
     * Deletes the user's token from Redis.
     *
     * @param token The token to be deleted.
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            redisService.deleteObject(getTokenKey(token));
        }
    }

    /**
     * Refreshes the token's expiration in Redis.
     *
     * @param loginUser The user whose token expiration is being refreshed.
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * MILLIS_SECOND);
        redisService.setCacheObject(getTokenKey(loginUser.getToken()), loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    /**
     * Verifies the token's validity and refreshes it if it is nearing expiration.
     *
     * @param loginUser The user whose token is being verified.
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * Generates the Redis key for the given token.
     *
     * @param token The token for which the key is being generated.
     * @return The Redis key.
     */
    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}