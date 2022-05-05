package com.nsu.stu.meet.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nsu.stu.meet.common.util.OwnUtil;
import com.nsu.stu.meet.model.DictItem;
import com.nsu.stu.meet.service.DictItemService;
import com.nsu.stu.meet.dao.DictItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author Xinhua X Yang
* @description 针对表【mt_dict_item】的数据库操作Service实现
* @createDate 2022-05-02 10:27:43
*/
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {

    private static final String prefix = "DICT_ITEM";

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public List<DictItem> getDictItemsByType(Integer typeId) {
        // 从缓存中获取
        String key = OwnUtil.getRedisKey(prefix, typeId);
        String s = redisTemplate.opsForValue().get(key);
        if (StringUtils.hasText(s)) {
            return JSON.parseArray(s, DictItem.class);
        }
        // 从数据库中获取
        List<DictItem> list = baseMapper.getDictItemsByType(typeId);
        // 存入缓存
        redisTemplate.opsForValue().set(key, JSON.toJSONString(list), 1, TimeUnit.DAYS);
        return list;
    }
}




