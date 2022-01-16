package com.yibo.product.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: huangyibo
 * @Date: 2021/8/21 22:43
 * @Description:
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/query")
    public String getProduct(String name){
        return "iphone-13";
    }
}
