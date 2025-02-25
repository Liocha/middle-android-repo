package com.example.androidpracticumcustomview

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.androidpracticumcustomview.ui.theme.CustomContainer
import com.example.androidpracticumcustomview.ui.theme.MainScreen

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Раскомментируйте нужный вариант
         */
        //startXmlPracticum() // «традиционный» android (XML)
        setContent { // Jetpack Compose
            MainScreen()
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun startXmlPracticum() {
        val customContainer = CustomContainer(this)
        customContainer.apply {
            setBackgroundColor(getColor(android.R.color.holo_green_light))
        }
        setContentView(customContainer)

        val firstView = TextView(this).apply {
            setText("First View ")
            textSize = 25f
            setTextColor(getColor(android.R.color.holo_red_light))
            setBackgroundColor(getColor(android.R.color.holo_blue_light))
        }

        customContainer.addView(firstView)

        val secondView = TextView(this).apply {
            text = "Second View"
            textSize = 50f
            setTextColor(getColor(android.R.color.black))
            setBackgroundColor(getColor(android.R.color.holo_purple))

        }
        // Добавление второго элемента через некоторое время
        Handler(Looper.getMainLooper()).postDelayed({
            customContainer.addView(secondView)
        }, 2000)
    }
}