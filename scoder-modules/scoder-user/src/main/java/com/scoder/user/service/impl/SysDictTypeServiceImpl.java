package com.scoder.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.scoder.common.core.domain.SysDictData;
import com.scoder.common.core.domain.SysDictType;
import com.scoder.user.mapper.SysDictDataMapper;
import com.scoder.user.mapper.SysDictTypeMapper;
import com.scoder.user.service.ISysDictTypeService;
import com.scoder.user.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Service implementation for handling dictionary operations.
 * Rewritten with MyBatis-Plus.
 *
 * @author Shawn Cui
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements ISysDictTypeService {

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    /**
     * Load dictionary cache data.
     */
    @Override
    public void loadingDictCache() {
        // Fetch all dictionary types
        List<SysDictType> dictTypeList = dictTypeMapper.selectList(null);

        // For each type, fetch and cache its corresponding dictionary data
        for (SysDictType dictType : dictTypeList) {
            List<SysDictData> dictDatas = dictDataMapper.selectList(
                    new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, dictType.getDictType())
            );
            DictUtils.setDictCache(dictType.getDictType(), dictDatas);
        }
    }

    /**
     * Clear dictionary cache data.
     */
    @Override
    public void clearDictCache() {
        DictUtils.clearDictCache();
    }

    /**
     * Reset dictionary cache data.
     */
    @Override
    public void resetDictCache() {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * Load dictionary cache on application startup.
     */
    @PostConstruct
    public void initDictCache() {
        loadingDictCache();
    }
}