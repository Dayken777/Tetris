package com.example.tetris

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.tetris.models.AppModel
import com.example.tetris.storage.AppPreferences
import com.example.tetris.view.TetrisView


class GameActivity : AppCompatActivity() {
    var tvCurrentScore: TextView? = null
    var tvHighScore: TextView? = null

    private lateinit var tetrisView: TetrisView
    var appPreferences:AppPreferences? = null
    private val appModel: AppModel = AppModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        appPreferences = AppPreferences(this)
        appModel.setPreferences(appPreferences)

        val btnRestart = findViewById<Button>(R.id.btn_restart)
        val btnOpenStart: Button = findViewById(R.id.btn_open_start)
        tvHighScore = findViewById(R.id.tv_high_score_game)
        tvCurrentScore = findViewById(R.id.tv_current_score)
        tetrisView = findViewById(R.id.view_tetris)
        tetrisView.setActivity(this)
        tetrisView.setModel(appModel)
        tetrisView.setOnTouchListener(this::onTetrisViewTouch)
        btnOpenStart.setOnClickListener(this::btnStartClick)
        btnRestart.setOnClickListener(this::btnRestartClick)
        updateHighScore()
        updateCurrentScore()
    }

    private fun btnRestartClick(view: View) {
        appModel.restartGame()
    }
    private fun btnStartClick(view: View){
        val intent: Intent = Intent(this,StartActivity::class.java)
        startActivity(intent)
    }
    private fun onTetrisViewTouch(view: View, event: MotionEvent): Boolean {
        if (appModel.isGameOver() || appModel.isGameAwaitingStart()) {
            appModel.startGame()
            tetrisView.setGameCommandWidthDelay(AppModel.Motions.DOWN)
        } else if (appModel.isGameActive()){
            when (resolveTouchDirection(view,event)) {
                0 -> moveTetromino(AppModel.Motions.LEFT)
                1 -> moveTetromino(AppModel.Motions.ROTATE)
                2 -> moveTetromino(AppModel.Motions.DOWN)
                3 -> moveTetromino(AppModel.Motions.RIGHT)
            }
        }
        return true
    }

    private fun resolveTouchDirection(view: View, event: MotionEvent):Int {
        val x = event.x / view.width
        val y = event.y / view.height
        val direction: Int = if (y > x) {
            if(x > 1 - y) 2 else 0
        }
        else{
            if (x > 1 - y) 3 else 1
        }
        return direction
    }

    private fun moveTetromino(motion: AppModel.Motions){
        if (appModel.isGameActive()) {
            tetrisView.setGameCommand(motion)
        }
    }
    private fun updateHighScore() {
        tvHighScore?.text = "${appPreferences?.getHighScore()}"


    }
    private fun updateCurrentScore() {
        tvCurrentScore?.text = "0"
    }
}