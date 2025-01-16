package com.scoder.projects.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.scoder.projects.domain.Project;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author Shawn Cui
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

}
