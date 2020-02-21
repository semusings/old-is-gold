package com.aldieemaulana.president.response;

import com.aldieemaulana.president.model.Price;

import java.io.Serializable;
import java.util.List;

public class PriceResponse implements Serializable {

    private final static long serialVersionUID = -8520022947078899072L;
    private Integer status;
    private Integer total;
    private List<Price> price = null;

    /**
     * No args constructor for use in serialization
     */
    public PriceResponse() {
    }

    /**
     * @param total
     * @param price
     * @param status
     */
    public PriceResponse(Integer status, Integer total, List<Price> price) {
        super();
        this.status = status;
        this.total = total;
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

}
