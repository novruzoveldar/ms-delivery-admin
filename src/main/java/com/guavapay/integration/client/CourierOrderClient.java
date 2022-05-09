package com.guavapay.integration.client;

import com.guavapay.integration.config.CourierOrderFeignConfig;
import com.guavapay.integration.error.CourierOrderException;
import com.guavapay.model.dto.AllOrderHistoryDto;
import com.guavapay.model.dto.CourierOrderHistoryDto;
import com.guavapay.model.request.CourierOrderFilter;
import com.guavapay.model.request.OrderAssignRequest;
import com.guavapay.model.request.OrderStateChangeRequest;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(
        name = "ms-courier-order",
        url = "${application.service.courier-order.url}",
        path = "courier/order",
        primary = false,
        configuration = {CourierOrderFeignConfig.class}
)
public interface CourierOrderClient {

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping("/change/state")
    ResponseEntity<Object> changeState(@Valid @RequestBody OrderStateChangeRequest stateChangeRequest);

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping("/history/all")
    List<AllOrderHistoryDto> allOrderHistory(@Valid @RequestBody CourierOrderFilter orderFilter);

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping("/assign/{courierId}")
    AllOrderHistoryDto assignCourier(@Valid @RequestBody OrderAssignRequest orderAssignRequest);

    @ErrorHandling(defaultException = CourierOrderException.class)
    @PostMapping("/history")
    List<CourierOrderHistoryDto> orderHistory(@Valid @RequestBody CourierOrderFilter orderFilter);

}
