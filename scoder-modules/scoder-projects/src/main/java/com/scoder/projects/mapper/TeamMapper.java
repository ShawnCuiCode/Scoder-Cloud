package com.scoder.projects.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scoder.projects.domain.Team;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author Shawn Cui
 * @since 2024-12-30
 */
@Mapper
public interface TeamMapper extends BaseMapper<Team> {

}
