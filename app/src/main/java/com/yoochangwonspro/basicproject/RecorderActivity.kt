package com.yoochangwonspro.basicproject

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RecorderActivity : AppCompatActivity() {

    private val resetButton: Button by lazy {
        findViewById(R.id.recorde_reset_btn)
    }

    private val recodeButton: RecodeButton by lazy {
        findViewById(R.id.recorde_btn)
    }

    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    private var state = State.BEFORE_RECORDING
        set(value) {
            field = value
            // isEnabled 는 true, false 값으로 버튼의 클릭 가능 여부를 판단한다
            // AFTER_RECORDING 녹음이 끝났을 때
            // ON_PLAYING 녹음을 끝내고 재생했을 때
            // 클릭이 가능하도록 구현
            resetButton.isEnabled = (value == State.AFTER_RECORDING) || (value == State.ON_PLAYING)
            recodeButton.updateIconWithState(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)

        // 권한 요청
        requestAudioPermission()

        initViews()
        bindViews()
        initVariables()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // firstOrNull 첫번째 값이 비었으면 null 아니면 첫번째 값 return
        val audioRecordPermissionGranted =
            requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                    grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        // 권한 부여가 되어있지 않는 경우 앱 종료
        if (!audioRecordPermissionGranted) {
            finish()
        }
    }

    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initViews() {
        recodeButton.updateIconWithState(state)
    }

    private fun bindViews() {
        // 녹음이 끝났을 때
        // 끝난 녹음을 재생할 때 reset 이 가능하도록 구현
        resetButton.setOnClickListener {
            state = State.BEFORE_RECORDING
            // 재생 중일 때도 reset 버튼을 클릭할 수 있다
            stopPlaying()
        }

        recodeButton.setOnClickListener {
            when (state) {
                State.BEFORE_RECORDING -> {
                    startRecording()
                }
                State.ON_RECORDING -> {
                    stopRecording()
                }
                State.AFTER_RECORDING -> {
                    startPlaying()
                }
                State.ON_PLAYING -> {
                    stopPlaying()
                }
            }
        }
    }

    private fun initVariables() {
        state = State.BEFORE_RECORDING
    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        state = State.AFTER_RECORDING
    }

    private fun startPlaying() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
        }
        player?.start()
        state = State.ON_PLAYING
    }

    private fun stopPlaying() {
        player?.release()
        player = null
        state = State.AFTER_RECORDING
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}