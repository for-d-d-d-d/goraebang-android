package com.fd.goraebang.consts;
import com.fd.goraebang.BuildConfig;


public class URL {
	public static final String SITE_URL = "http://www.20s.kr";
	public static final String BABLABS_API_URL = "https://bablabs.com/openapi/v1/";

	public static final String WEBVIEW_NOTICE		= SITE_URL + "/pages/notice";
	public static final String WEBVIEW_FAQ			= SITE_URL + "/pages/faq";
	public static final String WEBVIEW_AGREEMENT	= SITE_URL + "/pages/agreement";
	public static final String WEBVIEW_PRIVACY		= SITE_URL + "/pages/privacy";
	public static final String WEBVIEW_LICENCE		= SITE_URL + "/pages/license";

	public static final String DEFAULT_PROFILE = GET_API_URL() + "/media/etc/default_profile.png";

    public static final String GET_API_URL() {
        final String url = BuildConfig.DEBUG ? "http://192.168.43.180:8000/api/" : "http://api.twentylabs.co.kr/";
        return url;
    }
}
