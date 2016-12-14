package com.fd.goraebang.consts;
import com.fd.goraebang.BuildConfig;


public class URL {
    public static final String GET_API_URL() {
        final String url = BuildConfig.DEBUG ? "http://52.78.160.188/api/" : "http://52.78.160.188/api/";
        return url;
    }
}
