package com.nb.shiro.service;

import com.nb.shiro.beans.VO.AreaIndexVO;
import com.nb.shiro.beans.VO.AreaPinYinVO;
import com.nb.shiro.entity.SysArea;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-04-09
 */
public interface SysAreaService extends IService<SysArea> {

    List<AreaPinYinVO> findIndexList();

}
