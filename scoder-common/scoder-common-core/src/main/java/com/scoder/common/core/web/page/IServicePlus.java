package com.scoder.common.core.web.page;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Custom Service Interface.
 * Provides extended methods to convert database entities into DTO/VO objects.
 *
 * @param <T> Entity type
 * @author Shawn Cui
 * @since 2021-05-13
 */
public interface IServicePlus<T> extends IService<T> {

    /**
     * Retrieve a VO by ID.
     *
     * @param kClass VO class type
     * @param id     Primary key
     * @return VO object
     */
    default <K> K getVoById(Serializable id, Class<K> kClass) {
        T t = getBaseMapper().selectById(id);
        return BeanUtil.toBean(t, kClass);
    }

    /**
     * Retrieve a VO by ID with a custom converter.
     *
     * @param id        Primary key
     * @param convertor Function to convert the entity to VO
     * @param <K>       VO type
     * @return VO object
     */
    default <K> K getVoById(Serializable id, Function<T, K> convertor) {
        T t = getBaseMapper().selectById(id);
        return convertor.apply(t);
    }

    /**
     * Retrieve a list of VOs by IDs.
     *
     * @param kClass VO class type
     * @param idList List of primary keys
     * @return List of VOs
     */
    default <K> List<K> listVoByIds(Collection<? extends Serializable> idList, Class<K> kClass) {
        List<T> list = getBaseMapper().selectBatchIds(idList);
        return list == null ? null : list.stream().map(any -> BeanUtil.toBean(any, kClass)).collect(Collectors.toList());
    }

    /**
     * Retrieve a list of VOs by IDs with a custom converter.
     *
     * @param idList    List of primary keys
     * @param convertor Function to convert the entity to VO
     * @param <K>       VO type
     * @return List of VOs
     */
    default <K> List<K> listVoByIds(Collection<? extends Serializable> idList, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectBatchIds(idList);
        return list == null ? null : convertor.apply(list);
    }

    /**
     * Retrieve a list of VOs by column conditions.
     *
     * @param columnMap Map of column conditions
     * @param kClass    VO class type
     * @return List of VOs
     */
    default <K> List<K> listVoByMap(Map<String, Object> columnMap, Class<K> kClass) {
        List<T> list = getBaseMapper().selectByMap(columnMap);
        return list == null ? null : list.stream().map(any -> BeanUtil.toBean(any, kClass)).collect(Collectors.toList());
    }

    /**
     * Retrieve a list of VOs by column conditions with a custom converter.
     *
     * @param columnMap Map of column conditions
     * @param convertor Function to convert the entity to VO
     * @param <K>       VO type
     * @return List of VOs
     */
    default <K> List<K> listVoByMap(Map<String, Object> columnMap, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectByMap(columnMap);
        return list == null ? null : convertor.apply(list);
    }

    /**
     * Retrieve a single VO by wrapper conditions.
     *
     * @param queryWrapper Wrapper for conditions
     * @param kClass       VO class type
     * @return VO object
     */
    default <K> K getVoOne(Wrapper<T> queryWrapper, Class<K> kClass) {
        return BeanUtil.toBean(getOne(queryWrapper, true), kClass);
    }

    /**
     * Retrieve a single VO by wrapper conditions with a custom converter.
     *
     * @param queryWrapper Wrapper for conditions
     * @param convertor    Function to convert the entity to VO
     * @param <K>          VO type
     * @return VO object
     */
    default <K> K getVoOne(Wrapper<T> queryWrapper, Function<T, K> convertor) {
        return convertor.apply(getOne(queryWrapper, true));
    }

    /**
     * Retrieve a list of VOs by wrapper conditions.
     *
     * @param queryWrapper Wrapper for conditions
     * @param kClass       VO class type
     * @return List of VOs
     */
    default <K> List<K> listVo(Wrapper<T> queryWrapper, Class<K> kClass) {
        List<T> list = getBaseMapper().selectList(queryWrapper);
        return list == null ? null : list.stream().map(any -> BeanUtil.toBean(any, kClass)).collect(Collectors.toList());
    }

    /**
     * Retrieve a list of VOs by wrapper conditions with a custom converter.
     *
     * @param queryWrapper Wrapper for conditions
     * @param convertor    Function to convert the entity to VO
     * @param <K>          VO type
     * @return List of VOs
     */
    default <K> List<K> listVo(Wrapper<T> queryWrapper, Function<Collection<T>, List<K>> convertor) {
        List<T> list = getBaseMapper().selectList(queryWrapper);
        return list == null ? null : convertor.apply(list);
    }

    /**
     * Retrieve all VOs.
     *
     * @param kClass VO class type
     * @return List of VOs
     */
    default <K> List<K> listVo(Class<K> kClass) {
        return listVo(Wrappers.emptyWrapper(), kClass);
    }

    /**
     * Retrieve all VOs with a custom converter.
     *
     * @param convertor Function to convert the entity to VO
     * @return List of VOs
     */
    default <K> List<K> listVo(Function<Collection<T>, List<K>> convertor) {
        return listVo(Wrappers.emptyWrapper(), convertor);
    }

    /**
     * Retrieve paginated VOs by wrapper conditions.
     *
     * @param page         Pagination object
     * @param queryWrapper Wrapper for conditions
     * @param kClass       VO class type
     * @return Paginated result
     */
    default <K> PagePlus<T, K> pageVo(PagePlus<T, K> page, Wrapper<T> queryWrapper, Class<K> kClass) {
        PagePlus<T, K> result = getBaseMapper().selectPage(page, queryWrapper);
        List<K> voList = result.getRecords().stream().map(any -> BeanUtil.toBean(any, kClass)).collect(Collectors.toList());
        return result.setRecordsVo(voList);
    }

    /**
     * Retrieve paginated VOs by wrapper conditions with a custom converter.
     *
     * @param page         Pagination object
     * @param queryWrapper Wrapper for conditions
     * @param convertor    Function to convert the entity to VO
     * @return Paginated result
     */
    default <K> PagePlus<T, K> pageVo(PagePlus<T, K> page, Wrapper<T> queryWrapper, Function<Collection<T>, List<K>> convertor) {
        PagePlus<T, K> result = getBaseMapper().selectPage(page, queryWrapper);
        return result.setRecordsVo(convertor.apply(result.getRecords()));
    }

    /**
     * Retrieve paginated VOs without conditions.
     *
     * @param page   Pagination object
     * @param kClass VO class type
     * @return Paginated result
     */
    default <K> PagePlus<T, K> pageVo(PagePlus<T, K> page, Class<K> kClass) {
        return pageVo(page, Wrappers.emptyWrapper(), kClass);
    }

    /**
     * Retrieve paginated VOs without conditions with a custom converter.
     *
     * @param page      Pagination object
     * @param convertor Function to convert the entity to VO
     * @return Paginated result
     */
    default <K> PagePlus<T, K> pageVo(PagePlus<T, K> page, Function<Collection<T>, List<K>> convertor) {
        return pageVo(page, Wrappers.emptyWrapper(), convertor);
    }
}