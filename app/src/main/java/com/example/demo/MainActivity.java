package com.example.demo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    ConfigReceiver mReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript if needed

        // Add the interface to the WebView
        mWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        // Load a URL into the WebView
        // mWebView.loadUrl("file:///android_asset/index.html");

        // Register receiver
        mReceiver = new ConfigReceiver(mWebView);
        IntentFilter filter = new IntentFilter("com.example.demo.SEND_CONFIG");
        registerReceiver(mReceiver, filter);

        // Send a broadcast to trigger the onReceive method
        sendBroadcast();
    }

    public void onDestroy() {
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void sendBroadcast() {
        // ?:: 非常奇怪，必须要send之后，才能通过adb shell控制
        // Send a broadcast with a message
        Intent intent = new Intent("com.example.demo.SEND_CONFIG");
        intent.putExtra("text", "");
        sendBroadcast(intent);
    }
}
