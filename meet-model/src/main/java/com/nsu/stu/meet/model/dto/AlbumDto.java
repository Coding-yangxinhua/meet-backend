package com.nsu.stu.meet.model.dto;

import com.nsu.stu.meet.model.Album;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumDto extends Album {
    private Integer total;
}
