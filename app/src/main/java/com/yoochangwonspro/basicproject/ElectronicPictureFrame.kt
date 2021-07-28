package com.yoochangwonspro.basicproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ElectronicPictureFrame : AppCompatActivity() {

    private val addPhotoButton: Button by lazy {
        findViewById(R.id.add_photo_btn)
    }

    private val startPhotoFrameModeButton: Button by lazy {
        findViewById(R.id.start_photo_frame_mode_btn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_electronic_picture_frame)

        initAddPhotoButton()
        initStartPhotoFrameModeButton()
    }

    private fun initAddPhotoButton() {

    }

    private fun initStartPhotoFrameModeButton() {

    }
}