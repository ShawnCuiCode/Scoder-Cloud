package com.scoder.user.mapper;

import com.scoder.common.core.web.page.BaseMapperPlus;
import com.scoder.user.api.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper interface for managing user-related database operations (sys_user table).
 * Extends {@link BaseMapperPlus} to provide basic CRUD functionalities.
 * Includes additional custom queries for user management, such as avatar updates and password resets.
 *
 * @author Shawn Cui
 */
public interface SysUserMapper extends BaseMapperPlus<SysUser> {

    /**
     * Retrieves a paginated list of users based on search criteria.
     *
     * @param sysUser The user information used as filter criteria.
     * @return A list of users matching the criteria.
     */
    List<SysUser> selectUserList(SysUser sysUser);

    /**
     * Inserts a new user record into the database.
     *
     * @param user The user information to be inserted.
     * @return The number of rows affected.
     */
    int insertUser(SysUser user);

    /**
     * Updates user information in the database.
     *
     * @param user The user information to be updated.
     * @return The number of rows affected.
     */
    int updateUser(SysUser user);

    /**
     * Updates the avatar of a user.
     *
     * @param userName The username of the user.
     * @param avatar   The new avatar URL.
     * @return The number of rows affected.
     */
    int updateUserAvatar(@Param("userName") String userName, @Param("avatar") String avatar);

    /**
     * Resets the password of a user.
     *
     * @param userName The username of the user.
     * @param password The new password to set.
     * @return The number of rows affected.
     */
    int resetUserPwd(@Param("userName") String userName, @Param("password") String password);

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     * @return The number of rows affected.
     */
    int deleteUserById(Long userId);

    /**
     * Deletes multiple users by their IDs.
     *
     * @param userIds The IDs of the users to delete.
     * @return The number of rows affected.
     */
    int deleteUserByIds(Long[] userIds);

    /**
     * Checks if a username is unique in the database.
     *
     * @param userName The username to check.
     * @return The number of occurrences of the username.
     */
    int checkUserNameUnique(String userName);

    /**
     * Checks if an email is unique in the database.
     *
     * @param email The email to check.
     * @return The user associated with the email, or {@code null} if not found.
     */
    SysUser checkEmailUnique(String email);
}