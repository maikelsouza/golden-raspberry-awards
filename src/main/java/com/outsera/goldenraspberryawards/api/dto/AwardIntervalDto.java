package com.outsera.goldenraspberryawards.api.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AwardIntervalDto {

    private List<ProductorDto> min;

    private List<ProductorDto> max;
}
