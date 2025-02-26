package com.example.androidpracticumcustomview


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
Задание:
Реализуйте необходимые компоненты.
*/

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(onClick = { navigateToComposeScreen() }) {
                    Text(text = "COMPOSE CONTAINER")
                }

                Spacer(modifier = Modifier.width(20.dp))

                Button(onClick = { navigateToViewGroupScreen() }) {
                    Text(text = "VIEWGROUP CONTAINER")
                }
            }
        }
    }

    private fun navigateToComposeScreen() {
        val intent = Intent(
            this, ComposeActivity::class.java
        )
        startActivity(intent)
    }

    private fun navigateToViewGroupScreen() {
        val intent = Intent(
            this, ViewGroupActivity::class.java
        )
        startActivity(intent)
    }
}
