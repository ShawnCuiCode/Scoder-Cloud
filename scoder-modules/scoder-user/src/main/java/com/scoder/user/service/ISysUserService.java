package com.scoder.user.service;

import com.scoder.common.core.web.page.IServicePlus;
import com.scoder.user.api.domain.SysUser;

/**
 * Service interface for managing user-related operations.
 * Provides methods for CRUD operations, validation, and user-specific actions.
 * Extends {@link IServicePlus} to include MyBatis-Plus support for enhanced functionality.
 *
 * @author Shawn Cui
 */
public interface ISysUserService extends IServicePlus<SysUser> {

    /**
     * Retrieves a user by their username.
     *
     * @param userName The username of the user.
     * @return The user object, or {@code null} if not found.
     */
    SysUser selectUserByUserName(String userName);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user.
     * @return The user object, or {@code null} if not found.
     */
    SysUser selectUserById(Long userId);

    /**
     * Checks whether a username is unique in the system.
     *
     * @param userName The username to check.
     * @return A message indicating whether the username is unique.
     */
    String checkUserNameUnique(String userName);

    /**
     * Checks whether an email address is unique in the system.
     *
     * @param user The user object containing the email to check.
     * @return A message indicating whether the email is unique.
     */
    String checkEmailUnique(SysUser user);

    /**
     * Adds a new user to the system.
     *
     * @param user The user object containing user information.
     * @return The number of rows affected.
     */
    int insertUser(SysUser user);

    /**
     * Updates user information in the system.
     *
     * @param user The updated user object.
     * @return The number of rows affected.
     */
    int updateUser(SysUser user);

    /**
     * Updates the profile information of a user.
     *
     * @param user The user object containing the updated profile information.
     * @return The number of rows affected.
     */
    int updateUserProfile(SysUser user);

    /**
     * Updates the avatar of a user.
     *
     * @param userName The username of the user.
     * @param avatar   The URL of the new avatar.
     * @return {@code true} if the avatar was updated successfully, {@code false} otherwise.
     */
    boolean updateUserAvatar(String userName, String avatar);

    /**
     * Resets a user's password.
     *
     * @param user The user object containing the updated password.
     * @return The number of rows affected.
     */
    int resetPwd(SysUser user);

    /**
     * Resets a user's password based on their username.
     *
     * @param userName The username of the user.
     * @param password The new password to set.
     * @return The number of rows affected.
     */
    int resetUserPwd(String userName, String password);

    /**
     * Deletes multiple users by their IDs.
     *
     * @param userIds An array of user IDs to delete.
     * @return The number of rows affected.
     */
    int deleteUserByIds(Long[] userIds);
}