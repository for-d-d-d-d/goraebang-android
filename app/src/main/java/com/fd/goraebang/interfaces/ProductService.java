package com.fd.goraebang.interfaces;

import com.fd.goraebang.model.Mall;
import com.fd.goraebang.model.lists.ListProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductService {
    @GET("products/")
    Call<ListProduct> getProducts(@Query("page") int page);

    @GET("malls/")
    Call<List<Mall>> getMalls();
}