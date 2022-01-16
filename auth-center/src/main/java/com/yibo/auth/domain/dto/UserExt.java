package com.yibo.auth.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: huangyibo
 * @Date: 2021/8/19 1:51
 * @Description:
 */

@Data
public class UserExt extends UserDTO {

    //权限信息
    private List<MenuDTO> permission;
}
