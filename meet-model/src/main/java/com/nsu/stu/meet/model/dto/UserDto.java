package com.nsu.stu.meet.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nsu.stu.meet.model.User;
import com.nsu.stu.meet.model.enums.RelationEnums;

public class UserDto extends User {
    private RelationEnums relationStatus;

}
