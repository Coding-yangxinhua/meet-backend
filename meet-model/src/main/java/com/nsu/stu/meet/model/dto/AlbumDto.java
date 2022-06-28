package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Album;
import com.nsu.stu.meet.model.enums.LimitEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto extends Album {
    private Integer total;

    private String limitDesc;

    private boolean isSelf;

    public String getLimitDesc() {
        return getLimitId().desc();
    }

    @Override
    public Long getQueryId() {
        return getAlbumId();
    }
}
