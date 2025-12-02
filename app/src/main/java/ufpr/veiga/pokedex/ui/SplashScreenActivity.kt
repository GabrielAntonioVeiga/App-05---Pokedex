package ufpr.veiga.pokedex.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import ufpr.veiga.pokedex.R


class SplashScreenActivity : AppCompatActivity() {

    private val SPLACH_TIME_OUT: Long = 3000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.postDelayed({

        }, SPLACH_TIME_OUT)

    }
}