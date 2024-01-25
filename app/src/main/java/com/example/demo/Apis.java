package com.example.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.webkit.WebView;

public class Apis {
    Context mContext;
    AppMemoryReceiver mReceiver = null;
    WebView mWebView = null;

    Apis(Context c, WebView w) {
        mContext = c;
        mWebView = w;
        mReceiver = new AppMemoryReceiver();
        IntentFilter filter = new IntentFilter("com.example.demo.SEND_CONFIG");
        mContext.registerReceiver(mReceiver, filter);
    }

    public void onDestroy() {
        if (mReceiver != null)
            mContext.unregisterReceiver(mReceiver);
    }

    public class AppMemoryReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract the text from the broadcast intent
            String appMemory = intent.getStringExtra("text");

            // todo:: assuming the appMemory already has formatted to JSON by gpt
            String config = appMemory;

            // add appMemory to shared preferences
            SharedPreferences prefs = context.getSharedPreferences("com.example.demo", Context.MODE_PRIVATE);
            prefs.edit().putString("Config", config).apply();
            // reload webview
            // todo:: i think it's unsafe to reload in this method,
            //  should use a channel or message queue to consider the concurrency condition
            mWebView.reload();
        }
    }
}


