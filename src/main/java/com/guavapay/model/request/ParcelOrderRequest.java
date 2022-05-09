package com.guavapay.model.request;

import com.guavapay.model.dto.Measurement;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParcelOrderRequest {

    private Long accountId;
    private List<ProductRequest> productRequestList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRequest {
        private String productColor;
        private String productDescription;
        @NotNull
        private String productName;
        private String productPrice;
        @NotNull
        private Measurement measurement;
    }
}
