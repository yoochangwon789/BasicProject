package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculator.*
import org.w3c.dom.Text
import java.lang.NumberFormatException

class CalculatorActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById(R.id.expression_text_view)
    }

    private val resultTextView: TextView by lazy {
        findViewById(R.id.calculator_result_text_view)
    }

    private var isOperator = false

    private var hasOperator = false

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

        if (isOperator) {
            expressionTextView.append(" ")
        }

        isOperator = false

        val expressionText = expressionTextView.text.split(" ")

        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            return
        }
        else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_LONG).show()
            return
        }

        expressionTextView.append(number)

        // TODO : resultTextView 실시간으로 계산 결과를 넣어야 하는 기능
    }

    private fun operatorButtonClicked(operator: String) {

        if (expressionTextView.text.isEmpty()) {
            return
        }

        when {
            // 연산자를 이미 입력했는데 다른 연산자를 입력한 경우
            isOperator -> {
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) + operator
            }
            // 연산자를 2개 이상 입력한경우
            hasOperator -> {
                Toast.makeText(this, "연산자는 2개이상 사용할 수 없습니다.", Toast.LENGTH_LONG).show()
                return
            }
            else -> {
                // 숫자를 입력하고 연산자를 한번도 입력을 하지 않은 경우
                expressionTextView.append(" $operator")
            }
        }

        // 연산자일 경우에는 Text 에 색깔을 넣어주기 위한 기능 작업
        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            expressionTextView.text.length - 1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true
    }

    fun historyButtonClicked(v: View) {

    }

    fun clearButtonClicked(v: View) {

    }

    fun resultButtonClicked(v: View) {

    }
    
    private fun calculatorExpression(): String {
        val expressionTexts = expressionTextView.text.split(" ")
        
        // not -> 아직 연산자가 없는 경우 와 숫자 연산자 숫자 String size 가 3이상이 아닌 경우
        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        }
        // 연산자를 뺀 앞의 숫자와 뒤의 숫자가 정수가 아니면 이 조건을 실행
        else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }
    }
}

// 객체. 으로 시작하는 함수는 그 객체의 확장함수를 만들어 주는 것이다
fun String.isNumber(): Boolean {
    return try {
        // toBigInteger 타입으로 정상적으로 치환이 되면 true 반환
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        // 정상 적으로 변환이 되지 않으면 NumberFormatException 로 처리
        false
    }
}