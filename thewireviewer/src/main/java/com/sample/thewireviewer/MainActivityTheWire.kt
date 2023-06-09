package com.sample.thewireviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.characterviewer.MainActivity

class MainActivityTheWire : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_the_wire)

        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.putExtra("apiURL", "theWire")
        startActivity(intent)
    }
}