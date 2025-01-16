package com.scoder.file.api.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Represents file information.
 * <p>
 * This class provides details about a file, including its name and URL location.
 * It uses Lombok annotations for boilerplate code reduction.
 *
 * @author Shawn Cui
 */
@Data // Lombok annotation to generate getter, setter, equals, hashCode, and toString methods.
@NoArgsConstructor // Lombok annotation to generate a no-argument constructor.
@Accessors(chain = true) // Enables chaining of setters for a cleaner code style.
public class SysFile {

    /**
     * The name of the file.
     */
    private String name;

    /**
     * The URL where the file is located.
     */
    private String url;
}