package com.outsera.goldenraspberryawards.api.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ProducerAndYearDto {

    private String name;

    private Integer year;

}
