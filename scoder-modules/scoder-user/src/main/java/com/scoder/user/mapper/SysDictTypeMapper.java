package com.scoder.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scoder.common.core.domain.SysDictType;
import org.apache.ibatis.annotations.Mapper;

/**
 * Mapper interface for handling database operations related to dictionary types (sys_dict_type table).
 * Extends the MyBatis-Plus {@link BaseMapper} to provide standard CRUD operations.
 * <p>
 * This interface is annotated with {@link @Mapper} to enable MyBatis scanning for Spring integration.
 * Additional custom query methods can be added as required.
 *
 * @author Shawn Cui
 */
@Mapper
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    // Custom query methods for dictionary types can be added here if necessary.
}