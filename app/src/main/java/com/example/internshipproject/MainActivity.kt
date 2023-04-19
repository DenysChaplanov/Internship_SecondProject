package com.example.internshipproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var playerOneTurn: Boolean = true
    private var roundCount: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttons = Array(3) { row ->
            Array(3) { col ->
                initButton(row, col)
            }
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            resetGame()
        }
    }

    private fun initButton(row: Int, col: Int): Button {
        val button = findViewById<Button>(resources.getIdentifier("button$row$col", "id", packageName))
        button.setOnClickListener {
            onButtonClicked(button, row, col)
        }
        return button
    }

    private fun onButtonClicked(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) {
            return
        }
        if (playerOneTurn) {
            button.text = "X"
        } else {
            button.text = "O"
        }

        roundCount++

        if (checkForWin()) {
            if (playerOneTurn) {
                showToast("Player One Wins!")
            } else {
                showToast("Player Two Wins!")
            }
            disableButtons()
        } else if (roundCount == 9) {
            showToast("Draw!")
        } else {
            playerOneTurn = !playerOneTurn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { row ->
            Array(3) { col ->
                buttons[row][col].text.toString()
            }
        }
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0].isNotEmpty()) {
                return true
            }
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i].isNotEmpty()) {
                return true
            }
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0].isNotEmpty()) {
            return true
        }
        if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2].isNotEmpty()) {
            return true
        }
        return false
    }

    private fun disableButtons() {
        for (row in buttons) {
            for (button in row) {
                button.isEnabled = false
            }
        }
    }

    private fun resetGame() {
        for (row in buttons) {
            for (button in row) {
                button.isEnabled = true
                button.text = ""
            }
        }
        playerOneTurn = true
        roundCount = 0
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
