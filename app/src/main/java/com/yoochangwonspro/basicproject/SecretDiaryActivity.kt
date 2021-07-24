package com.yoochangwonspro.basicproject

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton

class SecretDiaryActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.number_picker1)
            .apply {
                this.minValue = 0
                this.maxValue = 9
            }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.number_picker2)
            .apply {
                this.minValue = 0
                this.maxValue = 9
            }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.number_picker3)
            .apply {
                this.minValue = 0
                this.maxValue = 9
            }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.open_btn)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById<AppCompatButton>(R.id.change_password_btn)
    }

    private val changePasswordMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secret_diary)

        numberPicker1
        numberPicker2
        numberPicker3

        openButton.setOnClickListener {
            val passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE)
            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                // 패스워드 일치

                // TODO : 다이러이 페이지 작성 후에 넘겨주어야함
                // startActivity()
            } else {
                // 실패 창 띄우는 기능 AlertDialog
                AlertDialog.Builder(this)
                    .setTitle("실패!!")
                    .setMessage("비밀번호가 잘되었습니다.")
                    .setPositiveButton("확인") { _,_ -> }
                    .create()
                    .show()
            }
        }

        changePasswordButton.setOnClickListener {
            if (changePasswordMode)
        }
    }
}