package com.example.tetris

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tvGreeting = findViewById<TextView>(R.id.tv_greeting)
        val btnClickMe = findViewById<Button>(R.id.btn_click_me)
        btnClickMe.setOnClickListener {
            if(TextUtils.isEmpty(tvGreeting.text)){
                tvGreeting.text="Hello World!"
            }
            else {
                Toast.makeText(this,"I have been clicked!",Toast.LENGTH_LONG).show()
            }
        }
    }
}