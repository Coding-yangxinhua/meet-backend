package com.nsu.stu.meet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.io.Serializable;

public class CheckModel implements Serializable {
    @JsonIgnore
    public Long getQueryId() {
        return null;
    }
}
