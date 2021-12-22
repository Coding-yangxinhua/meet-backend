package com.nsu.stu.meet.common.util;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class OwnUtil {
    public static <S, T> IPage<T> transIPageRecords2T(IPage<S> src, Class<T> tType) {
        if (src == null) {
            return null;
        } else {
            Page<T> newPage = (Page) BeanUtils.instantiateClass(Page.class);
            BeanUtils.copyProperties(src, newPage);
            newPage.setRecords(OwnUtil.copy2Ts(src.getRecords(), tType));
            return newPage;
        }
    }
    public static <T, S> List<T> copy2Ts(Collection<S> src, Class<T> tType) {
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

}
