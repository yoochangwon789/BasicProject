package com.yoochangwonspro.basicproject

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecorderActivity : AppCompatActivity() {

    private val recodeButton: RecodeButton by lazy {
        findViewById(R.id.recorde_btn)
    }

    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

    private var recorder: MediaRecorder? = null
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath}/recording.3gp"
    }

    private var state = State.BEFORE_RECORDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)

        // 권한 요청
        requestAudioPermission()

        initViews()
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

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        }
        recorder?.start()
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}