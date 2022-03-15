package com.nsu.stu.meet.common.util;

import com.alibaba.excel.util.CollectionUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.util.*;

@UtilityClass
public class OwnUtil {
    private Set<String> imageSet = new HashSet<String>() {
        {
            add(".jpeg");
            add(".jpg");
            add(".png");
        }
    };

    public <T, S> List<T> copy2Ts(Collection<S> src, Class<T> tType) {
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

}
