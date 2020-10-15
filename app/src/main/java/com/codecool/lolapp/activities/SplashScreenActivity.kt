package com.codecool.lolapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.codecool.lolapp.MainActivity
import com.codecool.lolapp.R
import com.codecool.lolapp.util.DELAY_SPLASH
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    lateinit var handler: Handler
    lateinit var anim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        anim = AnimationUtils.loadAnimation(this, R.anim.splash)
        lolLogo.startAnimation(anim)
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, DELAY_SPLASH)

    }
}