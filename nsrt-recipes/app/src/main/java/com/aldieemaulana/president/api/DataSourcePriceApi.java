package com.aldieemaulana.president.api;

import android.content.Context;

import com.aldieemaulana.president.model.Price;
import com.aldieemaulana.president.response.PriceResponse;

import java.util.LinkedList;
import java.util.List;

public class DataSourcePriceApi implements PriceApi {

    private final DatabaseHelper helper;

    public DataSourcePriceApi(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    @Override
    public AppOptional<PriceResponse> getPrices() {
        return AppOptional.of(priceResponse(helper.getAllPrices()));
    }

    @Override
    public AppOptional<PriceResponse> getPriceByItemId(final int id) {
        List<Price> filters = new LinkedList<>();
        filters.add(helper.getPrice(id));
        return AppOptional.of(priceResponse(filters));
    }

    @Override
    public AppOptional<PriceResponse> create(Price price) {
        helper.insertPrice(price);
        return getPrices();
    }

    @Override
    public void delete(Price price) {
        helper.delete(price);
    }

    @Override
    public void update(Price price) {
        helper.update(price);
    }

    private PriceResponse priceResponse(List<Price> prices) {
        PriceResponse response = new PriceResponse();
        response.setPrice(prices);
        response.setStatus(200);
        response.setTotal(prices.size());
        return response;
    }

}
