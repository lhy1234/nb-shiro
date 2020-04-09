package com.nb.shiro.mapper;

import com.nb.shiro.beans.VO.AreaIndexVO;
import com.nb.shiro.entity.SysArea;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-04-09
 */
@Repository
public interface SysAreaMapper extends BaseMapper<SysArea> {

    List<AreaIndexVO> findIndexList();

}
