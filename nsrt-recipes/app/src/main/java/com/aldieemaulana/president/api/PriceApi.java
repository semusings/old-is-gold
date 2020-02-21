package com.aldieemaulana.president.api;

import com.aldieemaulana.president.model.Price;
import com.aldieemaulana.president.response.PriceResponse;


public interface PriceApi {

    AppOptional<PriceResponse> getPrices();

    AppOptional<PriceResponse> getPriceByItemId(int id);

    AppOptional<PriceResponse> create(Price price);

    void delete(Price price);

    void update(Price price);
}
