package com.scoder.user.service;

import com.scoder.common.core.domain.SysDictType;
import com.scoder.common.core.web.page.IServicePlus;

/**
 * Dictionary Service Layer Interface
 * Handles dictionary operations.
 *
 * @author Shawn Cui
 */
public interface ISysDictTypeService extends IServicePlus<SysDictType> {

    /**
     * Load dictionary cache data.
     */
    void loadingDictCache();

    /**
     * Clear dictionary cache data.
     */
    void clearDictCache();

    /**
     * Reset dictionary cache data.
     */
    void resetDictCache();
}