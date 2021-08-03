package com.example.showsapp.ui.SplashActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.showsapp.R
import com.example.showsapp.ui.home.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

Handler().postDelayed({
        val intent = Intent(this, HomeActivity::class.java)
        this.startActivity(intent)
        finish()
},3000)


    }
}