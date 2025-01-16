package com.scoder.common.core.web.controller;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scoder.common.core.constant.HttpStatus;
import com.scoder.common.core.utils.DateUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.core.utils.sql.SqlUtil;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.common.core.web.page.PageDomain;
import com.scoder.common.core.web.page.TableDataInfo;
import com.scoder.common.core.web.page.TableSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * Generic data handling for web layer controllers.
 * <p>
 * Provides common utilities for data binding, pagination, and response formatting.
 *
 * @author Shawn Cui
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Automatically converts date strings from the front end to Date objects.
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Sets pagination data for the request.
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * Responds with paginated data.
     *
     * @param list the list of data to paginate
     * @return a table data response object
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setRows(list);
        rspData.setMsg("Query successful");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * Converts a number of affected rows to an Ajax response.
     *
     * @param rows the number of affected rows
     * @return an Ajax response indicating success or failure
     */
    protected AjaxResult<Void> toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * Converts a boolean result to an Ajax response.
     *
     * @param result the result of an operation
     * @return an Ajax response indicating success or failure
     */
    protected AjaxResult<Void> toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * Returns a successful Ajax response.
     */
    public AjaxResult<Void> success() {
        return AjaxResult.success();
    }

    /**
     * Returns an error Ajax response.
     */
    public AjaxResult<Void> error() {
        return AjaxResult.error();
    }

    /**
     * Returns a successful Ajax response with a custom message.
     *
     * @param message the success message
     * @return an Ajax response with the provided message
     */
    public AjaxResult<Void> success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * Returns an error Ajax response with a custom message.
     *
     * @param message the error message
     * @return an Ajax response with the provided message
     */
    public AjaxResult<Void> error(String message) {
        return AjaxResult.error(message);
    }

    /**
     * Redirects to a specified URL.
     *
     * @param url the URL to redirect to
     * @return a formatted redirect URL
     */
    public String redirect(String url) {
        return StrUtil.format("redirect:{}", url);
    }
}