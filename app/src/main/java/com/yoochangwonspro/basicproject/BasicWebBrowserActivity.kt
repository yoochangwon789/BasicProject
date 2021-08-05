package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class BasicWebBrowserActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.web_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basie_web_browser)
    }
}