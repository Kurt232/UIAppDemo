package com.example.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.WebView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class ConfigReceiver extends BroadcastReceiver {
    WebView mWebView;
    ConfigReceiver(WebView w){
        mWebView = w;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // Extract the text from the broadcast intent
        String appMemory = intent.getStringExtra("text");
        if (appMemory == null){
            Log.w("AppMemoryReceiver", "appMemory is null");
            return;
        }
        Log.i("AppMemoryReceiver", appMemory);
        // todo:: assuming the appMemory already has formatted to JSON by gpt
        // Decode Base64 to byte array
        byte[] decodedBytes = Base64.getDecoder().decode(appMemory);
        // Convert byte array to String
        String config = new String(decodedBytes, StandardCharsets.UTF_8);

        // add appMemory to shared preferences
        SharedPreferences prefs = context.getSharedPreferences("com.example.demo", Context.MODE_PRIVATE);
        prefs.edit().putString("Config", config).apply();
        // reload webview
        // todo:: i think it's unsafe to reload in this method,
        //  should use a channel or message queue given the concurrency condition
        // mWebView.reload();
        mWebView.loadUrl("file:///android_asset/index.html");
    }
}
