package com.scoder.user.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scoder.user.api.domain.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Represents a logged-in user's information.
 * <p>
 * This class contains details about the authenticated user, including their token,
 * login time, and system details. It also includes sensitive information like the password,
 * which is annotated with `@JsonIgnore` to prevent it from being serialized.
 * <p>
 * Lombok annotations are used to reduce boilerplate code.
 *
 * @Data: Generates getters, setters, equals, hashCode, and toString methods.
 * @NoArgsConstructor: Generates a no-argument constructor.
 * @AllArgsConstructor: Generates a constructor with all fields.
 * @Accessors(chain = true): Enables fluent-style setters.
 * @Author Shawn Cui
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class LoginUser implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures class consistency during deserialization

    /**
     * The authentication token for the user.
     */
    private String token;

    /**
     * The unique identifier of the user.
     */
    private Long userId;

    /**
     * The username of the user.
     */
    private String userName;

    /**
     * The password of the user.
     * <p>
     * This field is marked with @JsonIgnore to prevent it from being included in serialized JSON.
     */
    @JsonIgnore
    private String password;

    /**
     * The time the user logged in, represented in milliseconds since epoch.
     */
    private Long loginTime;

    /**
     * The time the authentication token expires, represented in milliseconds since epoch.
     */
    private Long expireTime;

    /**
     * The IP address of the user during login.
     */
    private String ipaddr;

    /**
     * The geographical location of the user during login.
     */
    private String loginLocation;

    /**
     * The browser used by the user during login.
     */
    private String browser;

    /**
     * The operating system used by the user during login.
     */
    private String os;

    /**
     * Detailed user information.
     * <p>
     * This is an instance of the SysUser class, which contains additional user-related data.
     */
    private SysUser sysUser;
}