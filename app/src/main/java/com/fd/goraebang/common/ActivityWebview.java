package com.fd.goraebang.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fd.goraebang.R;
import com.fd.goraebang.custom.CustomActivity;
import com.fd.goraebang.util.Utils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.net.MalformedURLException;
import java.net.URL;

@EActivity(R.layout.activity_webview)
public class ActivityWebview extends CustomActivity {
    @ViewById
    Toolbar toolbar;
    @ViewById
    WebView webView;
    @ViewById
    TextView tvToolbarTitle, tvToolbarUrl;

    String mTitle = null;
    String mUrl = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = getIntent().getStringExtra("title");
        mUrl = getIntent().getStringExtra("url");
        if(getIntent()!=null) {
            Uri uri = getIntent().getData();
            if (uri != null) {
                mUrl = uri.toString();
            }
        }
        if(mUrl == null || mUrl.length() < 11){
            mUrl = "#";
        }
    }

    @AfterViews
    void init(){
        toolbar.setContentInsetsAbsolute(0, 0);
        toolbar.setMinimumHeight(48);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new mWebViewClient());
        if(mTitle == null) //if mTitle is null, then set title receiver.
            webView.setWebChromeClient(new mWebViewChrome());
        else { //else set title
            tvToolbarTitle.setText(mTitle);
        }
        webView.loadUrl(mUrl);
    }

    private class mWebViewChrome extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (title != null) {
                mTitle = title;
                tvToolbarTitle.setText(title);
            }
        }

    }

    private class mWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            URL lURL = null;
            try {
                mUrl = url;
                lURL = new URL(url);
                String hostName = lURL.getHost();
                tvToolbarUrl.setText("http://" + hostName);
            } catch (MalformedURLException e) {
                tvToolbarUrl.setText("Not found.");
            }
        }

    }

    public void onClick(View v) {
        Intent intent = null;

        switch(v.getId()){
            case R.id.btnClose:
                finish();
                break;
            case R.id.btnBack:
                if(webView.canGoBack()){
                    webView.goBack();
                }else{
                    Utils.showGlobalToast(this, "가장 이전 페이지입니다.");
                }
                break;
            case R.id.btnForward:
                if(webView.canGoForward()){
                    webView.goForward();
                }else {
                    Utils.showGlobalToast(this, "가장 최신 페이지입니다.");
                }
                break;
            default :
                break;
        }

        if(intent != null)
            startActivity(intent);
    }
}
