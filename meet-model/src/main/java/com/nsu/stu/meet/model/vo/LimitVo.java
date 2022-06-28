package com.nsu.stu.meet.model.vo;

import com.nsu.stu.meet.model.enums.LimitEnums;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LimitVo {
    private Long userId;

    private LimitEnums limitEnums;

    public Integer getLimitId() {
        if (limitEnums == null) {
            return -1;
        }
        return limitEnums.value();
    }

}
