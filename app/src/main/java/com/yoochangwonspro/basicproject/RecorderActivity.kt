package com.yoochangwonspro.basicproject

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecorderActivity : AppCompatActivity() {

    private val recodeButton: RecodeButton by lazy {
        findViewById(R.id.recorde_btn)
    }

    private val requiredPermissions = arrayOf(Manifest.permission.RECORD_AUDIO)

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
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


    }

    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    private fun initViews() {
        recodeButton.updateIconWithState(state)
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}