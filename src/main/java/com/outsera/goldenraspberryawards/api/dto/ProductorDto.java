package com.outsera.goldenraspberryawards.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductorDto {

    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;

    public Integer getInterval() {
        interval = followingWin - previousWin;
        return interval;
    }

}
