package com.guavapay.integration.error;

import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class ParcelOrderErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        ParcelOrderException parcelOrderException;
        try {
            parcelOrderException = new ParcelOrderException(methodKey, response);
        } catch (IOException ex) {
            throw new BaseException(ex.getMessage());
        }
        return parcelOrderException;
    }
}
