package com.nsu.stu.meet.common.constant;

import lombok.experimental.UtilityClass;

public class SystemConstants {
    /**
     * 错误提示
     */
    public static final String PASSWORD_ERROR = "账号或密码错误！";
    public static final String CODE_ERROR = "验证码错误！";
    public static final String PASSWORD_LENGTH_ERROR = "密码长度不足！";
    public static final String MOBILE_ERROR = "手机号格式错误！";
    public static final String PASSWORD_SAME_ERROR = "新密码不能和旧密码相同！";
    public static final String TOKEN_ERROR = "token过期，请重新登录！";
    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String SMS_ERROR = "短信发送失败";
    public static final String FILE_TYPE_ERROR = "文件类型不合法";
    public static final String FILE_SIZE_ERROR = "文件大小不合法";
    public static final String ALBUM_NOT_EXISTS_ERROR = "相册不存在";
    public static final String INFO_NOT_EXISTS = "信息不存在";
    public static final String NO_RIGHTS_VISIT = "无权访问";
    public static final String SELF_CHANGE_FAIL = "不能和自己建立关系";
    /**
     * 成功提示
     */
    public static final String REGISTER_SUCCESS = "注册成功~";
    public static final String LOGIN_SUCCESS = "登录成功~";
    public static final String UPDATE_PROFILE_SUCCESS = "保存成功~";
    public static final String UPDATE_PASSWORD_SUCCESS = "更新密码成功！";

    /**
     * 友情提示
     */
    public static final String USER_EXIST = "账号已存在";
    public static final String USER_NOT_EXIST = "账号不存在";
    public static final String BLOCK = "已拉黑";

    /**
     * 普通常量
     */
    public static final String TOKEN_NAME = "meet-token";
}
