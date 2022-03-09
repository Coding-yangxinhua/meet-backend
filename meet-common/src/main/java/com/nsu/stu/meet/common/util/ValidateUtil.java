package com.nsu.stu.meet.common.util;

import com.nsu.stu.meet.common.base.IllegalParamException;
import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ValidateUtil {

    public boolean isMobile(@Nullable String mobile) {
        if (!StringUtils.hasText(mobile)) {
            return false;
        }
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(16[5,6])|(17[0-8])|(18[0-9])|(19[1,5,8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public boolean isPassword(@Nullable String password) {
        return StringUtils.hasText(password) && password.length() >= 8;
    }
}
