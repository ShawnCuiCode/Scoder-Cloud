package com.scoder.user.controller;

import com.scoder.common.core.domain.SysDictData;
import com.scoder.common.core.web.domain.AjaxResult;
import com.scoder.user.utils.DictUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling dictionary-related operations.
 * Provides an endpoint to fetch dictionary data based on the dictionary type.
 *
 * @author Shawn Cui
 */
@RestController
@RequestMapping("/dict")
public class SysDictController {

    /**
     * Fetches dictionary data for the specified dictionary type.
     *
     * @param dictType The type of dictionary to retrieve (e.g., "team_skills", "team_goals").
     * @return An {@link AjaxResult} containing a list of {@link SysDictData} objects for the given type.
     */
    @GetMapping("/{dictType}")
    public AjaxResult<List<SysDictData>> getDictByDictType(@PathVariable(value = "dictType") String dictType) {
        // Retrieves dictionary data from the cache using the provided dictionary type
        return AjaxResult.success(DictUtils.getDictCache(dictType));
    }
}