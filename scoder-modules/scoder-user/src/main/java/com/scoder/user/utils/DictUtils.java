package com.scoder.user.utils;

import com.scoder.common.core.constant.Constants;
import com.scoder.common.core.domain.SysDictData;
import com.scoder.common.core.utils.SpringUtils;
import com.scoder.common.core.utils.StringUtils;
import com.scoder.common.redis.service.RedisService;

import java.util.Collection;
import java.util.List;

/**
 * Utility class for handling dictionary-related operations, including caching in Redis.
 *
 * @author Shawn Cui
 */
public class DictUtils {

    /**
     * Cache dictionary data.
     *
     * @param key       the cache key
     * @param dictDatas list of SysDictData to cache
     */
    public static void setDictCache(String key, List<SysDictData> dictDatas) {
        SpringUtils.getBean(RedisService.class).setCacheObject(getCacheKey(key), dictDatas);
    }

    /**
     * Retrieve cached dictionary data.
     *
     * @param key the cache key
     * @return list of SysDictData or null if not found
     */
    public static List<SysDictData> getDictCache(String key) {
        Object cacheObj = SpringUtils.getBean(RedisService.class).getCacheObject(getCacheKey(key));
        if (StringUtils.isNotNull(cacheObj)) {
            return StringUtils.cast(cacheObj);
        }
        return null;
    }

    /**
     * Remove specific dictionary data from cache.
     *
     * @param key the dictionary key
     */
    public static void removeDictCache(String key) {
        SpringUtils.getBean(RedisService.class).deleteObject(getCacheKey(key));
    }

    /**
     * Clear all dictionary caches.
     */
    public static void clearDictCache() {
        Collection<String> keys = SpringUtils.getBean(RedisService.class).keys(Constants.SYS_DICT_KEY + "*");
        SpringUtils.getBean(RedisService.class).deleteObject(keys);
    }

    /**
     * Get a cache key for dictionary data.
     *
     * @param configKey the dictionary key
     * @return full cache key
     */
    public static String getCacheKey(String configKey) {
        return Constants.SYS_DICT_KEY + configKey;
    }

    /**
     * Get SysDictData by dictCode from Redis.
     *
     * @param key      the cache key for the dictionary data
     * @param dictCode the dictionary code to search for
     * @return SysDictData object if found, otherwise null
     */
    public static SysDictData getSysDictDataByCode(String key, Long dictCode) {
        // Retrieve the list from Redis
        RedisService redisService = SpringUtils.getBean(RedisService.class);
        List<SysDictData> dictDataList = redisService.getCacheList(key);

        if (dictDataList != null) {
            // Find the matching SysDictData by dictCode
            return dictDataList.stream()
                    .filter(data -> data.getDictCode().equals(dictCode))
                    .findFirst()
                    .orElse(null);
        }

        return null; // Return null if list is not found in Redis
    }
}