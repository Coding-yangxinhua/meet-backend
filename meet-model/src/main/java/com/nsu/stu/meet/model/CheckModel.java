package com.nsu.stu.meet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;

public class CheckModel implements Serializable {

    /**
     * 提供待查询实体id
     * 通过id获得实体类，以此获得其所需权限与对应用户
     * @return
     */
    @JsonIgnore
    public Long getQueryId() {
        return null;
    }
}
