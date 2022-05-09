package com.guavapay.controller;

import com.guavapay.integration.client.ParcelOrderClient;
import com.guavapay.model.dto.ParcelOrderDto;
import com.guavapay.model.request.ParcelOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "parcel")
public class ParcelOrderController {

    private final ParcelOrderClient parcelOrderClient;

    @PostMapping(value = "/order", consumes = {"application/json"}, produces = {"application/json"})
    public ParcelOrderDto createParcelOrder(@Valid @RequestBody ParcelOrderRequest parcelOrderRequest) {
        return parcelOrderClient.createParcelOrder(parcelOrderRequest);
    }
}
