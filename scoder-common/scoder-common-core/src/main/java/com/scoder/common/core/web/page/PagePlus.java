package com.scoder.common.core.web.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Enhanced pagination object that supports both domain and VO entities.
 *
 * @param <T> Type of the domain entity
 * @param <K> Type of the VO entity
 * @author
 */
@Data
@Accessors(chain = true)
public class PagePlus<T, K> implements IPage<T> {

    /**
     * List of domain entities.
     */
    private List<T> records = Collections.emptyList();

    /**
     * List of VO entities.
     */
    private List<K> recordsVo = Collections.emptyList();

    /**
     * Total number of records.
     */
    private long total = 0L;

    /**
     * Number of records per page.
     */
    private long size = 10L;

    /**
     * Current page number.
     */
    private long current = 1L;

    /**
     * List of sorting orders.
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * Flag for optimizing COUNT SQL queries.
     */
    private boolean optimizeCountSql = true;

    /**
     * Flag indicating whether to perform a count query.
     */
    private boolean isSearchCount = true;

    /**
     * Flag indicating whether the count query used a cached value.
     */
    private boolean hitCount = false;

    /**
     * Identifier for count queries.
     */
    private String countId;

    /**
     * Maximum limit for pagination.
     */
    private Long maxLimit;

    public PagePlus() {
    }

    public PagePlus(long current, long size) {
        this(current, size, 0L);
    }

    public PagePlus(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PagePlus(long current, long size, boolean isSearchCount) {
        this(current, size, 0L, isSearchCount);
    }

    public PagePlus(long current, long size, long total, boolean isSearchCount) {
        if (current > 1L) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    public static <T, K> PagePlus<T, K> of(long current, long size) {
        return of(current, size, 0);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, boolean searchCount) {
        return of(current, size, 0, searchCount);
    }

    public static <T, K> PagePlus<T, K> of(long current, long size, long total, boolean searchCount) {
        return new PagePlus<>(current, size, total, searchCount);
    }

    @Override
    public String countId() {
        return this.getCountId();
    }

    @Override
    public Long maxLimit() {
        return this.getMaxLimit();
    }

    public PagePlus<T, K> addOrder(OrderItem... items) {
        this.orders.addAll(Arrays.asList(items));
        return this;
    }

    public PagePlus<T, K> addOrder(List<OrderItem> items) {
        this.orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return this.getOrders();
    }

    @Override
    public boolean optimizeCountSql() {
        return this.optimizeCountSql;
    }

    @Override
    public long getPages() {
        // Resolve issue with page calculation in specific scenarios.
        return IPage.super.getPages();
    }
}