package com.nsu.stu.meet.common.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BaseModel implements Serializable {

    @TableField(exist = false)
    private List<String> sortColumns;

    @JsonIgnore
    @TableField(exist = false)
    private String[] selectColumns;

    @JsonIgnore
    @TableField(exist = false)
    private Map<String, String> sortMap;

}
