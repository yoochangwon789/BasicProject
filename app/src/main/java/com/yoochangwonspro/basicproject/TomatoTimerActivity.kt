package com.yoochangwonspro.basicproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class TomatoTimerActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinutesTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private val seekBar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomato_timer)

        bindView()
    }

    private fun bindView() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                remainMinutesTextView.text = "%02d".format(progress)
            }

            // 새로운 시간 선택을 위해서 조작이 일어날 때
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                currentCountDownTimer?.cancel()
                currentCountDownTimer = null
            }

            // 바의 손을 때자마자 시작 되는 함수
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 엘비스 연산자 왼쪽이 널이면 오른쪽을 return
                seekBar ?: return
                // 분단위 이기 때문에 60 을 곱해주고 밀리세컨드니까 1000을 또 곱해준다
                currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L).start()
                currentCountDownTimer?.start()
            }
        })
    }

    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                // 1초마다 한번씩 갱신된다
                updateRemainTime(millisUntilFinished)
                updateSickBar(millisUntilFinished)
            }

            override fun onFinish() {
                updateRemainTime(0)
                updateSickBar(0)
            }
        }

    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000

        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

    // 1분 마다 sickBar 한칸 씩 줄어드는 변화
    private fun updateSickBar(remainMillis: Long) {
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}