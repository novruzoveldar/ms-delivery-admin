package com.guavapay.model.dto;

import com.guavapay.model.type.DeliveryState;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierOrderDto {

    private Long parcelId;
    private Date deliveryDate;
    private Date routeBeginDate;
    private DeliveryState state;
}
