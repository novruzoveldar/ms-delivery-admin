package com.guavapay.model.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CourierOrderChangeRequest {

    @NotNull
    private Long parcelId;
    @NotNull
    private String destination;
}
