package com.example.demo;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.util.Log;

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context. */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** the format is JSON */
    /** the config is created by GPT */
    @JavascriptInterface
    public String getLayoutConfig() {
        SharedPreferences prefs = mContext.getSharedPreferences("com.example.demo", Context.MODE_PRIVATE);
        String config = prefs.getString("Config", "");
        Log.i("getLayoutConfig", config);
        return config;
    }

    /** receive the event from web action */
    @JavascriptInterface
    public void receiveEvent(String data) {
        Log.i("receiveEvent", data);
    }
}
