package com.scoder.auth.controller;

import com.scoder.auth.form.LoginBody;
import com.scoder.auth.service.SysLoginService;
import com.scoder.common.core.domain.R;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.security.service.TokenService;
import com.scoder.user.api.domain.SysUser;
import com.scoder.user.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Token Management Controller
 * Handles login, logout, token refresh, and user registration operations.
 *
 * @author Shawn Cui
 */
@RestController
public class TokenController {

    // Service for managing tokens
    @Autowired
    private TokenService tokenService;

    // Service for handling login and registration
    @Autowired
    private SysLoginService sysLoginService;

    /**
     * User login endpoint.
     * Accepts user credentials and returns a token upon successful authentication.
     *
     * @param form LoginBody containing user credentials (username and password).
     * @return Response containing the generated token.
     */
    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form) {
        // Authenticate the user
        LoginUser userInfo = sysLoginService.login(form.getUserName(), form.getPassword());
        // Generate and return the login token
        return R.ok(tokenService.createToken(userInfo));
    }

    /**
     * User registration endpoint.
     * Allows new users to register and generates a token upon successful registration.
     *
     * @param user SysUser object containing user registration details.
     * @return Response containing the generated token.
     */
    @PostMapping("register")
    public R<?> register(@RequestBody SysUser user) {
        // Register the user
        LoginUser userInfo = sysLoginService.register(user);
        // Generate and return the login token
        return R.ok(tokenService.createToken(userInfo));
    }

    /**
     * User logout endpoint.
     * Invalidates the user's current token and logs them out.
     *
     * @param request HttpServletRequest containing user session details.
     * @return Response indicating the success of the logout operation.
     */
    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request) {
        // Retrieve the logged-in user's details
        LoginUser loginUser = tokenService.getLoginUser(request);
        // If a user is logged in, invalidate their token
        if (StringUtils.isNotNull(loginUser)) {
            tokenService.delLoginUser(loginUser.getToken());
        }
        return R.ok();
    }

    /**
     * Token refresh endpoint.
     * Renews the user's token if it is still valid.
     *
     * @param request HttpServletRequest containing user session details.
     * @return Response indicating the success of the token refresh operation.
     */
    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request) {
        // Retrieve the logged-in user's details
        LoginUser loginUser = tokenService.getLoginUser(request);
        // If a user is logged in, refresh their token
        if (StringUtils.isNotNull(loginUser)) {
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }
}