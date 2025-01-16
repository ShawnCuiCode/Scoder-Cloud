package com.scoder.auth.form;

/**
 * Represents the login request payload.
 * Contains user credentials required for authentication.
 *
 * @author Shawn Cui
 */
public class LoginBody {

    /**
     * The username of the user attempting to log in.
     */
    private String userName;

    /**
     * The password of the user attempting to log in.
     */
    private String password;

    /**
     * Retrieves the username.
     *
     * @return The username as a string.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username.
     *
     * @param userName The username to be set.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Retrieves the password.
     *
     * @return The password as a string.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password The password to be set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}