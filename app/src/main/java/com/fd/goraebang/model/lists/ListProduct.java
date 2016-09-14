package com.fd.goraebang.model.lists;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fd.goraebang.model.ListDefault;
import com.fd.goraebang.model.Product;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListProduct extends ListDefault {
    @JsonProperty("results")
    private List<Product> results;

    public List<Product> getResults() {
        return results;
    }

    public void setResults(List<Product> results) {
        this.results = results;
    }
}
