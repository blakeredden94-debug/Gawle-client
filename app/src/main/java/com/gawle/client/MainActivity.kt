package com.gawle.client

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.widget.*
import android.graphics.Color
import android.view.Gravity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(30, 30, 30, 30)
            setBackgroundColor(Color.rgb(18, 18, 28))
        }

        val title = TextView(this).apply {
            text = "GAWLE CLIENT"
            textSize = 32f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        layout.addView(title)

        val launch = Button(this).apply {
            text = "LAUNCH MINECRAFT"
            textSize = 18f

            setOnClickListener {
                val intent = packageManager
                    .getLaunchIntentForPackage("com.mojang.minecraftpe")

                if (intent != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Minecraft could not be found!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        layout.addView(launch)

        setContentView(layout)
    }
}
