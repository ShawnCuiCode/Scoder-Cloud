package com.scoder.common.core.web.page;

import com.scoder.common.core.utils.ServletUtils;

/**
 * Handles table data processing for pagination and sorting.
 *
 * @author Shawn Cui
 */
public class TableSupport {
    /**
     * Key for the current page number.
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * Key for the number of records per page.
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * Key for the column to sort by.
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * Key for the sorting direction ("desc" or "asc").
     */
    public static final String IS_ASC = "isAsc";

    /**
     * Encapsulates pagination details into a PageDomain object.
     *
     * @return a PageDomain object containing pagination and sorting information
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        return pageDomain;
    }

    /**
     * Builds and returns a pagination request object.
     *
     * @return a PageDomain object representing the pagination request
     */
    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}