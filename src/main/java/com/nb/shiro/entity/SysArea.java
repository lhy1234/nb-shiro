package com.nb.shiro.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_area")
public class SysArea implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String fullName;

    private Integer parentId;

    private String shortName;

    private Integer level;

    private String cityCode;

    private String zipCode;

    private String mergerName;

    private BigDecimal lng;

    private BigDecimal lat;

    private String pinyin;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
