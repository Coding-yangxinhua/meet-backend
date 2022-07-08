package com.nsu.stu.meet.common.base;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xinhua X Yang
 */
@Getter
@Setter
public class DataScopeParam {

    private String userColumn;

    private String limitColumn;

    private List<Long> userFollowIds;

    private List<Long> blockedEachIds;

    public DataScopeParam () {
        this.userColumn = "user_id";
        this.limitColumn = "limit_id";
        this.userFollowIds = new ArrayList<>();
        this.blockedEachIds = new ArrayList<>();
    }
}
