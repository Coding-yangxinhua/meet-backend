package com.nsu.stu.meet.dao;

import com.nsu.stu.meet.model.DictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_dict_item】的数据库操作Mapper
* @createDate 2022-05-02 10:27:43
* @Entity generator.domain.DictItem
*/
public interface DictItemMapper extends BaseMapper<DictItem> {
    public List<DictItem> getDictItemsByType (Integer typeId);

}




