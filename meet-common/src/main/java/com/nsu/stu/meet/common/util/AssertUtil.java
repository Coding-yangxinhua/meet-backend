package com.nsu.stu.meet.common.util;

import com.nsu.stu.meet.common.base.IllegalParamException;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class AssertUtil {

    public void objectNotNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalParamException(message);
        }
    }
    public void objectNull(Object obj, String message) {
        if (obj != null) {
            throw new IllegalParamException(message);
        }
    }
    public void isEmpty(@Nullable Collection<?> collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    public void isEmpty(@Nullable Map<?, ?> map, String message) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    public void isEmpty(@Nullable Object[] array, String message) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new IllegalParamException(message);
        }
    }

    public void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    public void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    public void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalParamException(message);
        }
    }

    public void notContain(@Nullable Collection<?> collection, Object obj, String message) {
        if (!CollectionUtils.isEmpty(collection) && collection.contains(obj)) {
            throw new IllegalParamException(message);
        }
    }

    public void contain(@Nullable Collection<?> collection, Object obj, String message) {
        if (CollectionUtils.isEmpty(collection) || !collection.contains(obj)) {
            throw new IllegalParamException(message);
        }
    }

    public void notContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new IllegalParamException(message);
        }
    }

    public void contain(@Nullable String textToSearch, String substring, String message) {
        if (!StringUtils.hasLength(textToSearch) || !StringUtils.hasLength(substring) || !textToSearch.contains(substring)) {
            throw new IllegalParamException(message);
        }
    }

    public void hasText(String text, String message) {
        AssertUtil.objectNotNull(text, message);
        if (!StringUtils.hasText(text)) {
            throw new IllegalParamException(message);
        }
    }

    public void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalParamException(message);
        }
    }

    public void hasNotText(String text, String message) {
        AssertUtil.objectNotNull(text, message);
        if (StringUtils.hasText(text)) {
            throw new IllegalParamException(message);
        }
    }

    public void hasNotLength(@Nullable String text, String message) {
        if (StringUtils.hasLength(text)) {
            throw new IllegalParamException(message);
        }
    }

    public void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalParamException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalParamException(message);
        }
    }

}
