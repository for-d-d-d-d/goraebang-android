package com.fd.goraebang.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Cart {
    @JsonProperty("user")
    private User user;
    @JsonProperty("ordersheet")
    private OrderSheet ordersheet;
    @JsonProperty("product")
    private Product product;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("option")
    private String option;
    @JsonProperty("created")
    private String created;
    @JsonProperty("tracking_number")
    private String tracking_number;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderSheet getOrdersheet() {
        return ordersheet;
    }

    public void setOrdersheet(OrderSheet ordersheet) {
        this.ordersheet = ordersheet;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTracking_number() {
        return tracking_number;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }
}
