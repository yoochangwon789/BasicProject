package com.yoochangwonspro.basicproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi_calculator.*

class BmiCalculator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)

        result_btn.setOnClickListener {
            // 값이 빈경우
            if (height_edit_text.text.isEmpty() || weight_edit_text.text.isEmpty()) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_LONG).show()
                // @ 뒤의 함수를 return 하겠다
                return@setOnClickListener
            }

            // 이 아래로는 빈값이 올 수 없음
            val height: Int = (height_edit_text.text.toString()).toInt()
            val weight: Int = (weight_edit_text.text.toString()).toInt()
            val result = (height * height) / weight

            val intent = Intent(this, ResultBmi::class.java)
            intent.putExtra("resultBmi", result)
            startActivity(intent)
        }
    }
}