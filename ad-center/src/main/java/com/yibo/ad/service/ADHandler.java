package com.yibo.ad.service;

import com.yibo.ad.dto.SeckillGoods;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.context.CanalContext;
import top.javatool.canal.client.handler.EntryHandler;
import top.javatool.canal.client.model.CanalModel;

/**
 * @Author: huangyibo
 * @Date: 2021/8/15 18:37
 * @Description:
 */

@Component
public class ADHandler implements EntryHandler<SeckillGoods> {

    @Override
    public void insert(SeckillGoods seckillGoods) {
        // CanalModel可以得到当前这次的库名和表名
        CanalModel canal = CanalContext.getModel();

        //业务操作
        //新增缓存操作等
    }

    @Override
    public void update(SeckillGoods before, SeckillGoods after) {
        //业务操作
        //更新缓存操作等
    }

    @Override
    public void delete(SeckillGoods seckillGoods) {
        //业务操作
        //删除缓存操作等
    }
}
