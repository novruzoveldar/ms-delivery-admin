package com.guavapay.integration.client;

import com.guavapay.integration.config.CourierOrderFeignConfig;
import com.guavapay.model.dto.CourierFilterDto;
import com.guavapay.model.request.CourierFilterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
        name = "ms-courier",
        url = "${application.service.courier-order.url}",
        path = "courier",
        primary = false,
        configuration = {CourierOrderFeignConfig.class}
)
public interface CourierClient {

    @PostMapping("/filter")
    List<CourierFilterDto> courierFilter(@Valid @RequestBody CourierFilterRequest courierFilterRequest);
}
