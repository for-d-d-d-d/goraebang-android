package com.fd.goraebang.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    @JsonProperty("name")
    private String name;
    @JsonProperty("mall")
    private Mall mall;
    @JsonProperty("url")
    private String url;
    @JsonProperty("category")
    private String category;
    @JsonProperty("price")
    private float price;
    @JsonProperty("origin_price")
    private float originPrice;
    @JsonProperty("image")
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(float originPrice) {
        this.originPrice = originPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
