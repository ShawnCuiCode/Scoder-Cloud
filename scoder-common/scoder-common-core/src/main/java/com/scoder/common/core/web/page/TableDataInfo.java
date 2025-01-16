package com.scoder.common.core.web.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Table pagination data object.
 *
 * @param <T> The type of the rows in the table.
 * @author
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@ApiModel("Table Pagination Data Object")
public class TableDataInfo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Total number of records.
     */
    @ApiModelProperty("Total number of records")
    private long total;

    /**
     * List of data rows.
     */
    @ApiModelProperty("List of data rows")
    private List<T> rows;

    /**
     * Message status code.
     */
    @ApiModelProperty("Message status code")
    private int code;

    /**
     * Message content.
     */
    @ApiModelProperty("Message content")
    private String msg;

    /**
     * Constructs a TableDataInfo object with data and total record count.
     *
     * @param list  List of data rows
     * @param total Total number of records
     */
    public TableDataInfo(List<T> list, long total) {
        this.rows = list;
        this.total = total;
    }
}