package com.scoder.common.datasource.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.scoder.common.datasource.mybatisplus.CreateAndUpdateMetaObjectHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for MyBatis-Plus.
 * <p>
 * This class sets up essential plugins such as pagination and optimistic locking,
 * and provides support for custom metadata handling.
 *
 * @author Shawn Cui
 */
@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
public class MybatisPlusConfig {

    /**
     * Configure MyBatis-Plus interceptors.
     *
     * @return MybatisPlusInterceptor with necessary plugins
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor());
        interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * Pagination plugin.
     * <p>
     * Automatically detects the database type and supports limiting the maximum number of rows per page.
     *
     * @return PaginationInnerInterceptor configured for MySQL
     */
    public PaginationInnerInterceptor paginationInnerInterceptor() {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setMaxLimit(-1L); // No limit on max rows per page
        return paginationInnerInterceptor;
    }

    /**
     * Optimistic locking plugin.
     * <p>
     * Ensures consistency in concurrent update operations.
     *
     * @return OptimisticLockerInnerInterceptor
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * Metadata handler for automatic filling of create and update fields.
     *
     * @return MetaObjectHandler
     */
    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new CreateAndUpdateMetaObjectHandler();
    }
}