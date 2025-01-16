package com.scoder.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scoder.common.core.constant.UserConstants;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.user.api.domain.SysUser;
import com.scoder.user.mapper.SysUserMapper;
import com.scoder.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Implementation of user-related business logic.
 *
 * @author Shawn Cui
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    /**
     * Fetches a user by their username.
     *
     * @param userName the username to search for.
     * @return the user object if found, otherwise null.
     */
    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUserName, userName));
    }

    /**
     * Fetches a user by their ID.
     *
     * @param userId the ID of the user to search for.
     * @return the user object if found, otherwise null.
     */
    @Override
    public SysUser selectUserById(Long userId) {
        System.out.println(userId);
        return userMapper.selectById(userId);
    }

    /**
     * Checks if the given username is unique.
     *
     * @param userName the username to check.
     * @return a constant indicating whether the username is unique.
     */
    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        return count > 0 ? UserConstants.NOT_UNIQUE : UserConstants.UNIQUE;
    }

    /**
     * Checks if the given email is unique for a user.
     *
     * @param user the user object containing the email to check.
     * @return a constant indicating whether the email is unique.
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && !info.getUserId().equals(userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the user object to insert.
     * @return the number of rows affected.
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        user.setUserId(IdWorker.getId())
                .setCreateTime(new Date())
                .setDelFlag("0")
                .setStatus("0");
        return userMapper.insert(user);
    }

    /**
     * Updates user details.
     *
     * @param user the user object containing updated details.
     * @return the number of rows affected.
     */
    @Override
    @Transactional
    public int updateUser(SysUser user) {
        return userMapper.updateById(user);
    }

    /**
     * Updates a user's profile.
     *
     * @param user the user object containing updated profile details.
     * @return the number of rows affected.
     */
    @Override
    public int updateUserProfile(SysUser user) {
        return userMapper.updateById(user);
    }

    /**
     * Updates a user's avatar.
     *
     * @param userName the username of the user.
     * @param avatar   the new avatar URL.
     * @return true if the update was successful, otherwise false.
     */
    @Override
    public boolean updateUserAvatar(String userName, String avatar) {
        return userMapper.updateUserAvatar(userName, avatar) > 0;
    }

    /**
     * Resets a user's password.
     *
     * @param user the user object with the new password set.
     * @return the number of rows affected.
     */
    @Override
    public int resetPwd(SysUser user) {
        return userMapper.updateById(user);
    }

    /**
     * Resets a user's password by username.
     *
     * @param userName the username of the user.
     * @param password the new password.
     * @return the number of rows affected.
     */
    @Override
    public int resetUserPwd(String userName, String password) {
        return userMapper.resetUserPwd(userName, password);
    }

    /**
     * Deletes users in bulk by their IDs.
     *
     * @param userIds the IDs of the users to delete.
     * @return the number of rows affected.
     */
    @Override
    @Transactional
    public int deleteUserByIds(Long[] userIds) {
        return userMapper.deleteUserByIds(userIds);
    }
}