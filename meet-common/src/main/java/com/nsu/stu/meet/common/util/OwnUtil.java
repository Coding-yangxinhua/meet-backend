package com.nsu.stu.meet.common.util;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nsu.stu.meet.common.base.BusinessException;
import com.nsu.stu.meet.common.base.ResponseEntity;
import com.nsu.stu.meet.common.constant.SystemConstants;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.*;

@UtilityClass
public class OwnUtil {
    private final Set<String>  imageSet = new HashSet<String>() {
        {
            add(".jpeg");
            add(".jpg");
            add(".png");
            add(".webp");
        }
    };

    public <T, S> List<T> covertL2L(Collection<S> src, Class<T> tType) {
        if (CollectionUtils.isEmpty(src)) {
            return Collections.emptyList();
        } else {
            ArrayList<T> result = new ArrayList<>(src.size());
            try {
                for (Object next : src) {
                    T tar = tType.newInstance();
                    BeanUtils.copyProperties(next, tar);
                    result.add(tar);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    }

    public <P,V> IPage<V> pageDtoCovert(IPage<P> pageInfoPo, Class<V> v) {
        // 创建Page对象，实际上是一个ArrayList类型的集合
        try {
            if (pageInfoPo != null) {

                IPage<V> page = new Page<>(pageInfoPo.getCurrent(), pageInfoPo.getSize());
                page.setTotal(pageInfoPo.getTotal());
                List<P> records = pageInfoPo.getRecords();
                List<V> vs = covertL2L(records, v);
                page.setRecords(vs);
                page.setTotal(pageInfoPo.getTotal());
                return page;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getFileSuffix(String fileName) {
        if (fileName == null) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));

    }

    public boolean checkFileIsImage(String fileName) {
        String suffix = getFileSuffix(fileName);
        return imageSet.contains(suffix.toLowerCase(Locale.ROOT));
    }

    public String getRedisKey(Object ...names) {
        return concatString('_', names);
    }

    public String concatString(char sep, Object ...names) {
        StringBuilder key = new StringBuilder();
        key.append(names[0]);
        for (int i = 1; i <names.length; i ++){
            key.append(sep).append(names[i]);
        }
        return key.toString();
    }

    public <T>IPage<T> records2Page(List<T> records, int page, int size) {
        IPage<T> Ipage = new Page<>();
        Ipage.setRecords(records);
        Ipage.setCurrent(page);
        Ipage.setSize(size);
        return Ipage;
    }

    public <T, V> V entity2Dto(T entity, Class<V> clazz) {
        try {
            V v = clazz.newInstance();
            BeanUtils.copyProperties(entity, v);
            return v;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new BusinessException("反射失败");
        }
    }

}
