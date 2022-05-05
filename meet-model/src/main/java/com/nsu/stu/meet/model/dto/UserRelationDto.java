package com.nsu.stu.meet.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsu.stu.meet.model.UserRelation;
import com.nsu.stu.meet.model.enums.RelationEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRelationDto extends UserRelation {
    @JsonIgnore
    @Override
    public Long getQueryId() {
        return this.getDestId();
    }
}
