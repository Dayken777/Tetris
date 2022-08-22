package com.example.tetris

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tetris.storage.AppPreferences
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class StartActivity : AppCompatActivity() {
    var tvHighScore: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        supportActionBar?.hide()
        val preferences = AppPreferences(this)

        val btnStartGame:Button = findViewById(R.id.btn_new_game)
        val btnResetScore: Button = findViewById(R.id.btn_reset_score)
        val btnExit:Button = findViewById(R.id.btn_exit)
        
        tvHighScore = findViewById(R.id.tv_high_score)
        tvHighScore?.text = "High score: ${preferences.getHighScore()}"

        btnExit.setOnClickListener(this::handleExitEvent)
        btnStartGame.setOnClickListener(this::onBtnNewGameClick)
        btnResetScore.setOnClickListener(this::onBtnResetHighScoreClick)
    }
    private fun handleExitEvent(view: View){
        finishAffinity()
    }
    private fun onBtnNewGameClick(view: View){
        val intent: Intent = Intent(this,GameActivity::class.java)
        startActivity(intent)
    }
    @SuppressLint("SetTextI18n")
    private fun onBtnResetHighScoreClick(view: View){
        val preferences = AppPreferences(this)
        preferences.clearHighScore()
        Snackbar.make(view, "Score successfully reset",
        Snackbar.LENGTH_SHORT).show()
        tvHighScore?.text = "High score: ${preferences.getHighScore()}"
    }
}