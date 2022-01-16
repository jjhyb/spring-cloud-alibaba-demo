package com.yibo.auth.client;

import com.yibo.auth.domain.dto.UserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: huangyibo
 * @Date: 2021/8/19 1:47
 * @Description:
 */
@FeignClient("user-center")
public interface UserFeignClient {

    //根据账号查询用户信息
    @GetMapping("/user/selectUserByUsername")
    public UserExt selectUserByUsername(@RequestParam("username") String username);
}
