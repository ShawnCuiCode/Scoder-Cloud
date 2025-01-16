package com.scoder.common.core.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for Bean operations.
 * <p>
 * This class provides utility methods for working with JavaBeans, including copying properties,
 * retrieving getter and setter methods, and verifying property name equality based on method names.
 *
 * @author Shawn Cui
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {
    /**
     * Index at which the property name starts in a Bean method name.
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /**
     * Regular expression to match getter methods.
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /**
     * Regular expression to match setter methods.
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Copies properties from the source object to the destination object.
     * <p>
     * This method uses Spring's {@code BeanUtils.copyProperties} to copy properties from one object
     * to another, suppressing any exceptions that may occur.
     *
     * @param dest the target object to which properties will be copied
     * @param src  the source object from which properties will be copied
     */
    public static void copyBeanProp(Object dest, Object src) {
        try {
            copyProperties(src, dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the list of setter methods of a given object.
     * <p>
     * This method identifies setter methods based on the naming convention (methods starting with "set"),
     * and ensures that they take exactly one parameter.
     *
     * @param obj the object whose setter methods will be retrieved
     * @return a list of setter methods
     */
    public static List<Method> getSetterMethods(Object obj) {
        List<Method> setterMethods = new ArrayList<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            Matcher matcher = SET_PATTERN.matcher(method.getName());
            if (matcher.matches() && method.getParameterTypes().length == 1) {
                setterMethods.add(method);
            }
        }
        return setterMethods;
    }

    /**
     * Retrieves the list of getter methods of a given object.
     * <p>
     * This method identifies getter methods based on the naming convention (methods starting with "get"),
     * and ensures that they take no parameters.
     *
     * @param obj the object whose getter methods will be retrieved
     * @return a list of getter methods
     */
    public static List<Method> getGetterMethods(Object obj) {
        List<Method> getterMethods = new ArrayList<>();
        Method[] methods = obj.getClass().getMethods();

        for (Method method : methods) {
            Matcher matcher = GET_PATTERN.matcher(method.getName());
            if (matcher.matches() && method.getParameterTypes().length == 0) {
                getterMethods.add(method);
            }
        }
        return getterMethods;
    }

    /**
     * Checks if the property names in two method names are equal.
     * <p>
     * This method assumes the standard JavaBean naming convention for methods (e.g., "getName" and "setName").
     * It extracts the property name from each method name (by skipping the prefix) and compares them.
     *
     * @param m1 the first method name
     * @param m2 the second method name
     * @return {@code true} if the property names are equal, {@code false} otherwise
     */
    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }
}