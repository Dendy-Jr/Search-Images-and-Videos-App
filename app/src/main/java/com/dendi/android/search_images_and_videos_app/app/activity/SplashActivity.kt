package com.dendi.android.search_images_and_videos_app.app.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dendi.android.search_images_and_videos_app.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}