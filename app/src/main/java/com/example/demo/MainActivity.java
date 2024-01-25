package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private Apis mApis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed

        // Add the interface to the WebView
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // Register apis
        mApis = new Apis(this, mWebView);

        // Load a URL into the WebView
        mWebView.loadUrl("file:///android_asset/index.html");

    }

    public void onDestroy() {
        mApis.onDestroy();
        super.onDestroy();
    }
}
