package com.yoochangwonspro.basicproject

import android.annotation.SuppressLint
import android.media.SoundPool
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

    private val soundPool = SoundPool.Builder().build()
    private var tickingSoundId: Int? = null
    private var bellSoundId: Int? = null

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomato_timer)

        bindView()
        initSounds()
    }

    override fun onResume() {
        super.onResume()
        soundPool.autoResume()
    }

    override fun onPause() {
        super.onPause()
        soundPool.autoPause()
    }

    private fun bindView() {
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // progress 호출로 인해서 onProgressChanged 호출된다
                // 그러면서 updateRemainTime 함수가 호출이 된다
                // 함수가 호출이 되면서 사용자 입장에서가 아닌 코드적 부분에서 초가 수지적 으로 바뀌는데
                // 사용자 입장에서 분을 바꾸게 되면 또 다운 카운트가 시작된다
                // 그래서 안드로이드가 코드적 부분과 사용자 부분에서 구분이 안되기 때문
                // 그러므로 onProgressChanged 함수에서 fromUser 을 활용해 조건을 처리한다
                // 무조건 사용자의 이벤트 에서만 이 함수를 실행 시키겠다
                if (fromUser) {
                    updateRemainTime(progress * 60 * 1000L)
                }
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

                tickingSoundId?.let { soundId ->
                    soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
                }
            }
        })
    }

    private fun initSounds() {
        tickingSoundId = soundPool.load(this, R.raw.timer_ticking, 1)
        bellSoundId = soundPool.load(this, R.raw.timer_bell, 1)
    }

    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                // 1초마다 한번씩 갱신된다
                updateRemainTime(millisUntilFinished)
                updateSickBar(millisUntilFinished)
            }

            override fun onFinish() {
                completeCountDown()
            }
        }

    private fun completeCountDown() {
        updateRemainTime(0)
        updateSickBar(0)

        soundPool.autoPause()
        bellSoundId?.let { soundId ->
            soundPool.play(soundId, 1F, 1F, 0, -1, 1F)
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
        // progress 호출로 인해서 onProgressChanged 호출된다
        // 그러면서 updateRemainTime 함수가 호출이 된다
        // 함수가 호출이 되면서 사용자 입장에서가 아닌 코드적 부분에서 초가 수지적 으로 바뀌는데
        // 사용자 입장에서 분을 바꾸게 되면 또 다운 카운트가 시작된다
        // 그래서 안드로이드가 코드적 부분과 사용자 부분에서 구분이 안되기 때문
        // 그러므로 onProgressChanged 함수에서 fromUser 을 활용해 조건을 처리한다
        seekBar.progress = (remainMillis / 1000 / 60).toInt()
    }
}