package com.nsu.stu.meet.service;

import com.nsu.stu.meet.model.DictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Xinhua X Yang
* @description 针对表【mt_dict_item】的数据库操作Service
* @createDate 2022-05-02 10:27:43
*/
public interface DictItemService extends IService<DictItem> {
    List<DictItem> getDictItemsByType(Integer typeId);

}
