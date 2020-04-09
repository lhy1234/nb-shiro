package com.nb.shiro.beans.VO;

import lombok.Data;

import java.util.List;

/**
 * Created by: 李浩洋 on 2020-04-09
 **/
@Data
public class AreaPinYinVO {

    private String firstLetter;
    private List<AreaIndexVO> list;
}
