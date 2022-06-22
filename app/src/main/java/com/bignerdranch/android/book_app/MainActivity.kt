package com.bignerdranch.android.book_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast,false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia,true)
    )
    private var blockButton: Array<Boolean> = arrayOf(true, true, true, true, true, true)
    private var currentIndex = 0
    private var rightAnswers = 0
    private var answeredQuestions = 0
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if (userAnswer==correctAnswer){
            rightAnswers++
        }
        if (answeredQuestions==questionBank.size-1) {
            val percent = (100/(questionBank.size.toDouble()))*rightAnswers
            Toast.makeText(this, "$rightAnswers/${questionBank.size} ($percent%)", Toast.LENGTH_LONG).show()
        }
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.button_false)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
        }
        trueButton.setOnClickListener {
            checkAnswer(true)
            blockButton[currentIndex] = false
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            answeredQuestions++
            Log.d("count", "true $answeredQuestions")
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            blockButton[currentIndex]=false
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            answeredQuestions++
            Log.d("count", "false $answeredQuestions")
        }
        nextButton.setOnClickListener {
            currentIndex = (currentIndex+1)%questionBank.size
            updateQuestion()
            if (blockButton[currentIndex]){
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            } else {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }
        }
        prevButton.setOnClickListener {
            currentIndex = if (currentIndex>0) {
                (currentIndex-1)
            } else {
                questionBank.size-1
            }
            updateQuestion()
            if (blockButton[currentIndex]){
                trueButton.isEnabled = true
                falseButton.isEnabled = true
            } else {
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }
        }
        updateQuestion()
        }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}