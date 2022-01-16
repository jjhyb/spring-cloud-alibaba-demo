package com.yibo.auth.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: huangyibo
 * @Date: 2021/8/19 1:52
 * @Description:
 */

@Data
public class MenuDTO {

    private String id;

    private String code;


    private String pCode;

    private String pId;

    private String menuName;

    private String url;

    private String isMenu;

    private Integer level;

    private Integer sort;

    private String status;

    private String icon;

    private Date createTime;

    private Date updateTime;
}
