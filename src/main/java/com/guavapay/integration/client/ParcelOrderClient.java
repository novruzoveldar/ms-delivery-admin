package com.guavapay.integration.client;

import com.guavapay.integration.config.ParcelOrderFeignConfig;
import com.guavapay.integration.error.ParcelOrderException;
import com.guavapay.model.dto.ParcelOrderDto;
import com.guavapay.model.request.ParcelOrderRequest;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@FeignClient(
        name = "ms-parcel-order",
        url = "${application.service.parcel-order.url}",
        path = "parcel",
        primary = false,
        configuration = {ParcelOrderFeignConfig.class}
)
public interface ParcelOrderClient {

    @ErrorHandling(defaultException = ParcelOrderException.class)
    @PostMapping( "/order")
    ParcelOrderDto createParcelOrder(@Valid @RequestBody ParcelOrderRequest parcelOrderRequest);
}
