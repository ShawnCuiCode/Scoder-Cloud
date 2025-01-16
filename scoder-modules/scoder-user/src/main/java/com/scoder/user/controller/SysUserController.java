package com.scoder.user.controller;

import com.scoder.common.core.constant.UserConstants;
import com.scoder.common.core.domain.R;
import com.scoder.common.core.utils.SecurityUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.core.utils.bean.BeanUtils;
import com.scoder.common.core.web.controller.BaseController;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.common.security.service.TokenService;
import com.scoder.file.api.RemoteFileService;
import com.scoder.user.api.domain.SysUser;
import com.scoder.user.api.model.LoginUser;
import com.scoder.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling user-related operations.
 * Provides endpoints for user management, including registration, profile updates, and avatar uploads.
 *
 * @author Shawn Cui
 */
@RestController
@RequestMapping
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RemoteFileService remoteFileService;

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all users wrapped in an {@link AjaxResult}.
     */
    @GetMapping("/getUserList")
    public AjaxResult<List<SysUser>> getUserList() {
        return AjaxResult.success(userService.list());
    }

    /**
     * Retrieves user details based on the username.
     *
     * @param username The username to search for.
     * @return User details wrapped in an {@link R} object.
     */
    @GetMapping("/getUserByUserName/{username}")
    public R<LoginUser> getUserByUserName(@PathVariable("username") String username) {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (sysUser == null) {
            return R.ok(null);
        } else {
            LoginUser loginUser = new LoginUser();
            BeanUtils.copyProperties(sysUser, loginUser);
            loginUser.setSysUser(sysUser);
            return R.ok(loginUser);
        }
    }

    /**
     * Retrieves the current user's details based on their token.
     *
     * @return Current user details wrapped in an {@link AjaxResult}.
     */
    @GetMapping("/getUserByToken")
    public AjaxResult<SysUser> getUserByToken() {
        LoginUser loginUser = tokenService.getLoginUser();
        return AjaxResult.success(loginUser.getSysUser());
    }

    /**
     * Retrieves user details by user ID.
     *
     * @param userId The user ID to search for.
     * @return User details wrapped in an {@link AjaxResult}.
     */
    @GetMapping("/{userId}")
    public AjaxResult<SysUser> getUserById(@PathVariable(value = "userId") Long userId) {
        return AjaxResult.success(userService.selectUserById(userId));
    }

    /**
     * Registers a new user.
     *
     * @param user User information for registration.
     * @return Result of the operation wrapped in an {@link AjaxResult}.
     */
    @PostMapping("/register")
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("Failed to add user. Username already exists.");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("Failed to add user. Email already exists.");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * Updates user details.
     *
     * @param user Updated user information.
     * @return Result of the operation wrapped in an {@link AjaxResult}.
     */
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("Failed to update user. Email already exists.");
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * Deletes users by their IDs.
     *
     * @param userIds Array of user IDs to delete.
     * @return Result of the operation wrapped in an {@link AjaxResult}.
     */
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * Resets a user's password.
     *
     * @param oldPassword The user ID whose password needs resetting.
     * @param newPassword The new password.
     * @return Result of the operation wrapped in an {@link AjaxResult}.
     */
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestParam String oldPassword, @RequestParam String newPassword) {
        Long userId = tokenService.getLoginUser().getUserId();
        SysUser user = userService.selectUserById(userId);
        String password = user.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("Current password does not match.");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("New password cannot be the same as the old one.");
        }
        user.setPassword(newPassword);
        return toAjax(userService.resetPwd(user));
    }

    /**
     * Updates the user's profile information.
     *
     * @param user Updated profile information.
     * @return Result of the operation wrapped in an {@link AjaxResult}.
     */
    @PutMapping("/updateProfile")
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("Failed to update profile. Email is not unique.");
        }
        if (userService.updateUserProfile(user) > 0) {
            LoginUser loginUser = tokenService.getLoginUser();
            loginUser.setSysUser(user);
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("Failed to update profile.");
    }
}