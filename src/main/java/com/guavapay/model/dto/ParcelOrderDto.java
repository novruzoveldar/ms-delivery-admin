package com.guavapay.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.guavapay.model.type.ItemStatusCode;
import com.guavapay.model.type.ParcelState;
import com.guavapay.util.deserializer.DateDeserializer;
import com.guavapay.util.serializer.DateSerializer;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcelOrderDto {

    private Long id;
    private ParcelState parcelState;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date placedDate;
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
    private Date deliveredDate;
    private BigDecimal itemPrice;
    private Integer itemQuantity;
    private ItemStatusCode statusCode;
}
