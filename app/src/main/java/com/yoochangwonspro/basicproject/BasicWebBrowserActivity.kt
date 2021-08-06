package com.yoochangwonspro.basicproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.ContentLoadingProgressBar
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class BasicWebBrowserActivity : AppCompatActivity() {

    private val goHomeButton: ImageButton by lazy {
        findViewById(R.id.web_home_button)
    }

    private val goBackButton: ImageButton by lazy {
        findViewById(R.id.web_go_back_button)
    }

    private val goForwardButton: ImageButton by lazy {
        findViewById(R.id.web_go_forward_button)
    }

    private val addressBar: EditText by lazy {
        findViewById(R.id.web_address_edit_text)
    }

    private val webView: WebView by lazy {
        findViewById(R.id.web_view)
    }

    private val refreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.refresh_layout)
    }

    private val progressBar: ContentLoadingProgressBar by lazy {
        findViewById(R.id.web_progress_var)
    }

    // 안드로이드에서 back 버튼을 눌렀을 때 호출되는 함수
    override fun onBackPressed() {
        // webView 에서 지원하는 canGoBack 를 이용해서 뒤로갈 수 있는지 판단하는 메서드
        if (webView.canGoBack()) {
            // history 에 뒤로갈 페이지가 있다면 실행
            webView.goBack()
        }else {
            // history 에 뒤로갈 페이지가 없다면 앱 종료
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basie_web_browser)

        initViews()
        bindViews()
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
        webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(DEFAULT_URL)
        }
    }

    private fun bindViews() {
        goHomeButton.setOnClickListener {
            webView.loadUrl(DEFAULT_URL)
        }

        // action 이 수행되었을 때 이벤트가 발생한다.
        // 파라미터로는 3개
        // v -> action 이 발생한 뷰
        // actionId -> 어떤 id 에서 액션이 발생했는지 알려주는데 imeOptions 을 actionDone 으로 설정했기 때문에
        // 우측하단에 Done 버튼을 클릭시에 이 이벤트가 발생하면서 이 id 에서는
        // actionDone 의 id 가 날라오게 될 것이다
        // event -> 사용자가 눌렀는지 떼었는지 발생하는 event 를 확인하는 파라미터
        // 이 모든 action 과 event 가 발생했을 때 return 값을 줘야하는데
        // true 를 주게 되면 내가 이곳에서 이벤트들을 처리했으니 다른곳에서 핸들링 하지 않아도 된다는 의미
        // false 를 주게 되면 나는 이것을 소비하지 않았으니까 다른 곳에서도 action 을 소비해야한다는 의미
        // 만약 Done 버튼을 누르게 되면 이 action 을 소비하고 닫는 것이기 때문에 만약 true 를 반환하게 되면
        // 키보드는 닫히고 false 를 return 하면 키보드는 닫히게 된다
        addressBar.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // 주소를 입력하고 DONE 버튼을 눌렀을 경우
                webView.loadUrl(v.text.toString())
            }

            return@setOnEditorActionListener false
        }

        goBackButton.setOnClickListener {
            webView.goBack()
        }

        goForwardButton.setOnClickListener {
            webView.goForward()
        }

        // 웹 뷰를 당겼을 때 리프레쉬가 일어나는 함수 부분
        refreshLayout.setOnRefreshListener {
            webView.reload()
        }
    }

    // isRefresh 을 false 값으로 바꿔줘야 로딩되는 아이콘이 사라진다
    // 페이지가 로딩된 다음에 없어지는게 좋으니까 WebViewClient 상속받아 load Finish 를 받아서 false 로 전달한다
    // inner 클래스로 선언해 상위에 있는 클래스의 property 를 접근할 수 있게 한다
    inner class WebViewClient: android.webkit.WebViewClient() {
        // 페이지 로딩이 끝났을 때
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            refreshLayout.isRefreshing = false
        }
    }

    companion object {
        private const val DEFAULT_URL = "http://www.google.com"
    }
}