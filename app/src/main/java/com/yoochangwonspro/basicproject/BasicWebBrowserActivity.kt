package com.yoochangwonspro.basicproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class BasicWebBrowserActivity : AppCompatActivity() {

    private val webView: WebView by lazy {
        findViewById(R.id.web_view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basie_web_browser)

        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        // 구글의 URL 을 이용했을 때 우리 웨브라우저가 아닌 외부 브라우저로 이동하는 이슈를 볼 수 있다.
        // 외냐하면 안드로이드는 기본 디폴트는 크롬이다 그래서 웹뷰 클라이언트를 내가 만든 webView 의 동작을
        // 오버라이드 시켜줘야 나의 웹 브라우저로 실행된다
        // 그러므로 나의 webView 에 webViewClient 로 매칭을 시켜준다

        // 그런데 어떤 아이콘을 클릭했을 때 이슈가 일어나지 않는 경우가 있다.
        // 이것은 웹이 자바스크립트로 구현이 되어 있는데 안드로이드 에서는 자바스크립트 언어의 부분을 보안상 사용하지 못하도록
        // 하고 있어서 자바스크립트를 사용하겠다고 따로 처리해 줘야한다
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("http://www.google.com")
    }
}