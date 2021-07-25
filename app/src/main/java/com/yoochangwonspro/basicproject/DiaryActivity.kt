package com.yoochangwonspro.basicproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class DiaryActivity : AppCompatActivity() {

    private val diaryEditText: EditText by lazy {
        findViewById(R.id.diary_edit_text)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        val detailPreferences = getSharedPreferences("diary", Context.MODE_PRIVATE)

    }
}