package com.scoder.auth.service;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.scoder.common.core.domain.R;
import com.scoder.common.core.enums.UserStatus;
import com.scoder.common.core.exception.BaseException;
import com.scoder.common.core.utils.SecurityUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.user.api.RemoteUserService;
import com.scoder.user.api.domain.SysUser;
import com.scoder.user.api.model.LoginUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Service class for handling user login and registration.
 *
 * @author Shawn Cui
 */
@Component
public class SysLoginService {

    @Autowired
    private RemoteUserService remoteUserService;

    /**
     * Handles user login.
     *
     * @param userName the username of the user.
     * @param password the password of the user.
     * @return the login user information upon successful authentication.
     * @throws BaseException if authentication fails or user is not found.
     */
    public LoginUser login(String userName, String password) {
        // Validate that username and password are not blank
        if (StringUtils.isAnyBlank(userName, password)) {
            throw new BaseException("userName or password can't be blank");
        }

        // Fetch user information from remote service
        R<LoginUser> userResult = remoteUserService.getUserInfo(userName);

        // Check for errors or missing data
        if (R.FAIL == userResult.getCode()) {
            throw new BaseException(userResult.getMsg());
        }
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            throw new BaseException("userName does not exist");
        }

        LoginUser userInfo = userResult.getData();
        SysUser user = userInfo.getSysUser();

        // Check for user status
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            throw new BaseException("sorry, your account has been deleted");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            throw new BaseException("sorry, your account has been disabled");
        }

        // Validate password
        if (!SecurityUtils.matchesPassword(password, user.getPassword())) {
            throw new BaseException("username or password is incorrect");
        }

        return userInfo;
    }

    /**
     * Handles user logout.
     *
     * @param loginName the name of the user to log out.
     */
    public void logout(String loginName) {
        // Logout functionality can be implemented here if needed
    }

    /**
     * Handles user registration.
     *
     * @param user the user object containing registration details.
     * @return the login user information upon successful registration.
     * @throws BaseException if registration fails or user already exists.
     */
    public LoginUser register(SysUser user) {
        // Check if the username already exists
        R<LoginUser> userResult = remoteUserService.getUserInfo(user.getUserName());

        if (R.FAIL == userResult.getCode()) {
            throw new BaseException(userResult.getMsg());
        }

        long userId = IdWorker.getId();

        // If user does not exist, proceed with registration
        if (StringUtils.isNull(userResult) || StringUtils.isNull(userResult.getData())) {
            user.setUserId(userId).setCreateTime(new Date());
            remoteUserService.registerUser(user);
        }

        // Prepare and return the login user information
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);
        loginUser.setSysUser(user);
        return loginUser;
    }
}