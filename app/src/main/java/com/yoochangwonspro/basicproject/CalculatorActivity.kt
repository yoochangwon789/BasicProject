package com.yoochangwonspro.basicproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.yoochangwonspro.basicproject.model.History
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

    private val historyLayout: View by lazy {
        findViewById<View>(R.id.history_layout)
    }

    private val historyLinearLayout: LinearLayout by lazy {
        findViewById<LinearLayout>(R.id.history_linearlayout)
    }

    lateinit var db: AppDatabase

    private var isOperator = false

    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // context, 내가사용할 DB, DB 의 이름 설정
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()
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

        // resultTextView 실시간으로 계산 결과를 넣어야 하는 기능
        resultTextView.text = calculatorExpression()

    }

    @SuppressLint("SetTextI18n")
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

    @SuppressLint("SetTextI18n")
    fun historyButtonClicked(v: View) {
        historyLayout.isVisible = true
        // LinearLayout 의 모든 뷰들이 삭제 됨
        historyLinearLayout.removeAllViews()

        // DB 에서 모든 기록 가져오기
        // 뷰에 모든 기록 할당하기
        Thread{
            db.historyDao().getAll().reversed().forEach {

                // 메인 쓰레드로 가는 방법
                // UI 는 메인 쓰레드 에서만 그릴 수 있기 때문
                runOnUiThread {
                    val historyView = LayoutInflater
                        .from(this)
                        .inflate(R.layout.history_row, null, false)
                    historyView.findViewById<TextView>(R.id.row_expression_text_view)
                        .text = it.expression
                    historyView.findViewById<TextView>(R.id.row_calculator_result_text_view)
                        .text = "= ${it.result}"

                    historyLinearLayout.addView(historyView)
                }
            }
        }.start()
    }

    fun closeHistoryButtonClicked(v: View) {
        historyLayout.isVisible = false
    }

    fun historyClearButtonClicked(v: View) {
        historyLinearLayout.removeAllViews()

        // DB 에서 모든 기록 삭제
        // 뷰에서 모든 기록 삭제
        Thread {
            db.historyDao().deleteAll()
        }.start()
    }
    fun clearButtonClicked(v: View) {
        expressionTextView.text = ""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    fun resultButtonClicked(v: View) {
        val expressionTexts = expressionTextView.text.split(" ")

        if (expressionTextView.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "아직 완전되지 않은 수식입니다.", Toast.LENGTH_LONG).show()
            return
        }

        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_LONG).show()
            return
        }

        val expressionText = expressionTextView.text.toString()
        val resultText = calculatorExpression()

        // DB 에 넣어주는 부분
        // MainThread 와 이 Thread 누가 먼저 실행되는 지는 알 수 없어서 text 같은 경우엔 위에서 미리저장해 두는 것이 좋다
        Thread(Runnable {
            db.historyDao().insertHistory(History(null, expressionText, resultText))
        }).start()

        resultTextView.text = ""
        expressionTextView.text = resultText

        isOperator = false
        hasOperator = false
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

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
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