package com.fd.goraebang.consts;
import com.fd.goraebang.BuildConfig;


public class URL {
    public static final String GET_API_URL() {
        final String url = BuildConfig.DEBUG ? "http://52.78.160.188/json/" : "http://52.78.160.188/json/";
        return url;
    }
}
