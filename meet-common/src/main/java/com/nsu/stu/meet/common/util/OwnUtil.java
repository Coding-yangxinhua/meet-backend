package com.nsu.stu.meet.common.util;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    public String getRedisKey(String key, Object ...names) {
        StringBuilder redisKey = new StringBuilder(key);
        String sep = "_";
        for (Object name:
             names) {
            redisKey.append(sep).append(String.valueOf(name));
        }
        return redisKey.toString();
    }

    public String getRedisKey(String key, String value) {
        return key + "_" + value;
    }

    public <T>IPage<T> records2Page(List<T> records, int page, int size) {
        IPage<T> Ipage = new Page<>();
        Ipage.setRecords(records);
        Ipage.setCurrent(page);
        Ipage.setSize(size);
        return Ipage;
    }

}
