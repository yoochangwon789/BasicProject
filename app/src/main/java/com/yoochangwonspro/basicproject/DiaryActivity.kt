package com.yoochangwonspro.basicproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val diaryEditText: EditText by lazy {
        findViewById(R.id.diary_edit_text)
    }

    // 메인 쓰레드에 연결돼 있는 Handler
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))

        val runnable = Runnable {
            getSharedPreferences("diary", Context.MODE_PRIVATE).edit {
                putString("detail", diaryEditText.text.toString())
            }
        }

        //  text 가 변경 될 때 마다 이 함수가 호출
        // 변경 될때마다 저장시킬 수 있도록 한다
        diaryEditText.addTextChangedListener {
            // 뷰에서 사용하는 Handler 함수를 통해 구현 -> post, postDelayer 함수 사용
            // removeCallbacks 를 사용해 설정된 0.5초 이전에 보류된 runnable 이 있다면 지우고 다시 실행
            handler.removeCallbacks(runnable)
            handler.postDelayed(runnable, 500)
        }
    }
}