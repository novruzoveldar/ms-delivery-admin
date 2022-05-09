package com.guavapay.model.dto;

import com.guavapay.model.type.DeliveryState;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourierOrderFilter {

    private List<DeliveryState> states;
    private Date from;
    private Date to;
    private int page;
    private int limit;
}
