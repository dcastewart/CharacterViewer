package com.sample.simpsonsviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.characterviewer.MainActivity

class MainActivitySimpsons : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_simpsons)

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("apiURL", "simpsons")
        startActivity(intent)
    }
}