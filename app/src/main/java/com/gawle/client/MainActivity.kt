package com.gawle.client

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 30, 24, 30)
            setBackgroundColor(Color.rgb(18, 18, 28))
        }

        val title = TextView(this).apply {
            text = "GAWLE CLIENT"
            textSize = 32f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        layout.addView(title)

        val subtitle = TextView(this).apply {
            text = "Minecraft Bedrock Companion"
            textSize = 15f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        layout.addView(subtitle)

        val features = listOf(
            "Zoom",
            "Shulker Peek",
            "Left-Handed Mode"
        )

        features.forEach { feature ->
            val button = Button(this).apply {
                text = feature
                textSize = 16f
            }

            layout.addView(button)
        }

        setContentView(layout)
    }
}
