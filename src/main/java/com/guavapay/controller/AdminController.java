package com.guavapay.controller;

import com.guavapay.integration.client.CourierClient;
import com.guavapay.integration.client.CourierOrderClient;
import com.guavapay.model.dto.AllOrderHistoryDto;
import com.guavapay.model.dto.CourierFilterDto;
import com.guavapay.model.dto.CourierOrderHistoryDto;
import com.guavapay.model.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "courier/order")
public class AdminController {

    private final CourierOrderClient courierOrderClient;
    private final CourierClient courierClient;

    @PostMapping(value = "/change/state")
    public ResponseEntity<Object> changeState(@Valid @RequestBody OrderStateChangeRequest stateChangeRequest) {
        return courierOrderClient.changeState(stateChangeRequest);
    }

    @PostMapping(value = "/history/all", consumes = {"application/json"}, produces = {"application/json"})
    public List<AllOrderHistoryDto> allOrderHistory(@Valid @RequestBody CourierOrderFilter orderFilter) {
        return courierOrderClient.allOrderHistory(orderFilter);
    }

    @PostMapping(value = "/assign/{courierId}", consumes = {"application/json"}, produces = {"application/json"})
    public AllOrderHistoryDto assignCourier(@Valid @RequestBody OrderAssignRequest orderAssignRequest) {
        return courierOrderClient.assignCourier(orderAssignRequest);
    }

    @PostMapping(value = "/history", consumes = {"application/json"}, produces = {"application/json"})
    public List<CourierOrderHistoryDto> orderHistory(@Valid @RequestBody CourierOrderFilter orderFilter) {
        return courierOrderClient.orderHistory(orderFilter);
    }

    @PostMapping(value = "/filter", consumes = {"application/json"}, produces = {"application/json"})
    public List<CourierFilterDto> courierFilter(@Valid @RequestBody CourierFilterRequest courierFilterRequest) {
        return courierClient.courierFilter(courierFilterRequest);
    }

}
