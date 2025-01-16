package com.scoder.common.core.utils;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for accessing Spring beans in non-Spring-managed environments.
 * <p>
 * Provides methods for retrieving beans from the Spring application context,
 * checking their properties, and accessing AOP proxy objects.
 * <p>
 * This class implements the {@link BeanFactoryPostProcessor} interface to
 * initialize the bean factory for later use.
 *
 * @author Shawn Cui
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor {

    /**
     * Spring's configurable listable bean factory, used to manage beans.
     */
    private static ConfigurableListableBeanFactory beanFactory;

    /**
     * Retrieves a bean from the Spring context by its name.
     *
     * @param name the name of the bean to retrieve
     * @return the bean instance
     * @throws BeansException if the bean cannot be retrieved
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * Retrieves a bean from the Spring context by its class type.
     *
     * @param clz the class type of the bean
     * @return the bean instance
     * @throws BeansException if the bean cannot be retrieved
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    /**
     * Checks if the bean factory contains a bean definition with the given name.
     *
     * @param name the name of the bean
     * @return true if the bean exists, false otherwise
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /**
     * Determines if a bean with the given name is a singleton.
     *
     * @param name the name of the bean
     * @return true if the bean is a singleton, false if it is a prototype
     * @throws NoSuchBeanDefinitionException if the bean does not exist
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * Retrieves the type of a bean with the given name.
     *
     * @param name the name of the bean
     * @return the class type of the bean
     * @throws NoSuchBeanDefinitionException if the bean does not exist
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * Retrieves the aliases of a bean with the given name.
     *
     * @param name the name of the bean
     * @return an array of aliases for the bean
     * @throws NoSuchBeanDefinitionException if the bean does not exist
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

    /**
     * Retrieves the AOP proxy object for a given instance.
     * <p>
     * This method is useful for accessing proxied objects when using
     * Spring AOP features.
     *
     * @param invoker the original object
     * @return the proxied object
     */
    @SuppressWarnings("unchecked")
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

    /**
     * Initializes the bean factory by setting the static reference.
     *
     * @param beanFactory the bean factory from the Spring context
     * @throws BeansException if there is an issue with the bean factory
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }
}