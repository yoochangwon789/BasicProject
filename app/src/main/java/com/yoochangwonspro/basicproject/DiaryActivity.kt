package com.yoochangwonspro.basicproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener

class DiaryActivity : AppCompatActivity() {

    private val diaryEditText: EditText by lazy {
        findViewById(R.id.diary_edit_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

        diaryEditText.setText(detailPreferences.getString("detail", ""))
        //  text 가 변경 될 때 마다 이 함수가 호출
        // 변경 될때마다 저장시킬 수 있도록 한다
        diaryEditText.addTextChangedListener {
            detailPreferences.edit {
                putString("detail", diaryEditText.text.toString())
            }
        }
    }
}