package com.nb.shiro.service.impl;

import com.google.common.collect.Maps;
import com.nb.shiro.beans.VO.AreaIndexVO;
import com.nb.shiro.beans.VO.AreaPinYinVO;
import com.nb.shiro.entity.SysArea;
import com.nb.shiro.mapper.SysAreaMapper;
import com.nb.shiro.service.SysAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 2020-04-09
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements SysAreaService {

    @Autowired
    private SysAreaMapper sysAreaMapper;
    @Override
    public List<AreaPinYinVO> findIndexList() {
        List<AreaIndexVO> list = sysAreaMapper.findIndexList();
        if(list != null && !list.isEmpty()){
            Map<String,List<AreaIndexVO>> maps = list.stream().collect(Collectors.groupingBy(AreaIndexVO::getPinyin));
            if(maps != null && !maps.isEmpty()){
                List<AreaPinYinVO> resultList = new ArrayList<>();
                maps.forEach((k,v)->{
                    AreaPinYinVO vo = new AreaPinYinVO();
                    vo.setFirstLetter(k);
                    vo.setList(v);
                    resultList.add(vo);
                });
                return resultList;
            }
        }
        return Collections.emptyList();
    }
}
