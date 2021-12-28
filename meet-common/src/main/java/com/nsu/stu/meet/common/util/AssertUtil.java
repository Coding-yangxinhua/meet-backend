package com.nsu.stu.meet.common.util;

import com.nsu.stu.meet.common.base.IllegalParamException;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class AssertUtil {

    public static void objectNotNull(@Nullable Object obj, String message) {
        if (obj == null) {
            throw new IllegalParamException(message);
        }
    }

    public static void isEmpty(@Nullable Collection<?> collection, String message) {
        if (!CollectionUtils.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    public static void isEmpty(@Nullable Map<?, ?> map, String message) {
        if (!CollectionUtils.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    public static void isEmpty(@Nullable Object[] array, String message) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new IllegalParamException(message);
        }
    }

    public static void notEmpty(@Nullable Collection<?> collection, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalParamException(message);
        }
    }

    public static void notEmpty(@Nullable Map<?, ?> map, String message) {
        if (CollectionUtils.isEmpty(map)) {
            throw new IllegalParamException(message);
        }
    }

    public static void notEmpty(@Nullable Object[] array, String message) {
        if (ObjectUtils.isEmpty(array)) {
            throw new IllegalParamException(message);
        }
    }

    public static void notContain(@Nullable Collection<?> collection, Object obj, String message) {
        if (!CollectionUtils.isEmpty(collection) && collection.contains(obj)) {
            throw new IllegalParamException(message);
        }
    }

    public static void contain(@Nullable Collection<?> collection, Object obj, String message) {
        if (CollectionUtils.isEmpty(collection) || !collection.contains(obj)) {
            throw new IllegalParamException(message);
        }
    }

    public static void notContain(@Nullable String textToSearch, String substring, String message) {
        if (StringUtils.hasLength(textToSearch) && StringUtils.hasLength(substring) && textToSearch.contains(substring)) {
            throw new IllegalParamException(message);
        }
    }

    public static void contain(@Nullable String textToSearch, String substring, String message) {
        if (!StringUtils.hasLength(textToSearch) || !StringUtils.hasLength(substring) || !textToSearch.contains(substring)) {
            throw new IllegalParamException(message);
        }
    }

    public static void hasText(@Nullable String text, String message) {
        if (!StringUtils.hasText(text)) {
            throw new IllegalParamException(message);
        }
    }

    public static void hasLength(@Nullable String text, String message) {
        if (!StringUtils.hasLength(text)) {
            throw new IllegalParamException(message);
        }
    }

    public static void hasNotText(@Nullable String text, String message) {
        if (StringUtils.hasText(text)) {
            throw new IllegalParamException(message);
        }
    }

    public static void hasNotLength(@Nullable String text, String message) {
        if (StringUtils.hasLength(text)) {
            throw new IllegalParamException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
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
