package com.nsu.stu.meet.common.base;


/**
 * @author Xinhua X Yang
 * 存储数据权限
 */
public class DataScopeHolder {
    private DataScopeHolder(){}
    public static final ThreadLocal<DataScopeParam> dataScopeThreadLocal = new ThreadLocal<>();

    public static void set(DataScopeParam dataScopeParam) {
        dataScopeThreadLocal.set(dataScopeParam);
    }

    public static DataScopeParam get() {
        return dataScopeThreadLocal.get();
    }

    public static void remove() {
        dataScopeThreadLocal.remove();
    }

}
