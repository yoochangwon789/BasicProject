package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RecorderActivity : AppCompatActivity() {

    private val recodeButton: RecodeButton by lazy {
        findViewById(R.id.recorde_btn)
    }

    private var state = State.BEFORE_RECORDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder)

        // 권한 요청

        initViews()
    }

    private fun requestAudioPermission() {
        requestPermissions()
    }

    private fun initViews() {
        recodeButton.updateIconWithState(state)
    }
}