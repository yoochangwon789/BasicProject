package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_lotto_lottery.*

class LottoLottery : AppCompatActivity() {

    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()
    private val numberTestViewList: List<TextView> by lazy {
        listOf(
            one_number,
            two_number,
            three_number,
            four_number,
            five_number,
            six_number
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto_lottery)

        // 넘버 픽커 숫자 범위
        number_piker.minValue = 1
        number_piker.maxValue = 45

        initRunButton()
        initAddButton()
        clearButton()
    }

    private fun initAddButton() {
        add_btn.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해 주세요", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 다섯개 까지만 선택할 수 있습니다", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(number_piker.value)) {
                Toast.makeText(this, "이미 선택한 번호 입니다.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val textView = numberTestViewList[pickNumberSet.size]
            textView.isVisible = true
            textView.text = number_piker.value.toString()

            when (number_piker.value) {
                in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yello)
                in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
                in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
                in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
                else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
            }

            pickNumberSet.add(number_piker.value)

        }
    }

    private fun initRunButton() {
        run_btn.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index, number ->
                val textView = numberTestViewList[index]

                textView.text = number.toString()
                textView.isVisible = true

                when (number) {
                    in 1..10 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_yello)
                    in 11..20 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_blue)
                    in 21..30 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_red)
                    in 31..40 -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_gray)
                    else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
                }
            }

            Log.d("MainActivity", list.toString())
        }
    }

    private fun clearButton() {
        clear_btn.setOnClickListener {
            pickNumberSet.clear()
            numberTestViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }

        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return newList.sorted()
    }
}