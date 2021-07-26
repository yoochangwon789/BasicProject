package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.zero_btn -> numberButtonClicked("0")
            R.id.one_btn -> numberButtonClicked("1")
            R.id.two_btn -> numberButtonClicked("2")
            R.id.three_btn -> numberButtonClicked("3")
            R.id.four_btn -> numberButtonClicked("4")
            R.id.five_btn -> numberButtonClicked("5")
            R.id.six_btn -> numberButtonClicked("6")
            R.id.seven_btn -> numberButtonClicked("7")
            R.id.eight_btn -> numberButtonClicked("8")
            R.id.nine_btn -> numberButtonClicked("9")
            R.id.plus_btn -> operatorButtonClicked("+")
            R.id.minus_btn -> operatorButtonClicked("-")
            R.id.multi_btn -> operatorButtonClicked("*")
            R.id.divider_btn -> operatorButtonClicked("/")
            R.id.modulo_btn -> operatorButtonClicked("%")
        }
    }

    private fun numberButtonClicked(number: String) {

    }

    private fun operatorButtonClicked(operator: String) {

    }

    fun historyButtonClicked(v: View) {

    }

    fun clearButtonClicked(v: View) {

    }

    fun resultButtonClicked(v: View) {

    }
}