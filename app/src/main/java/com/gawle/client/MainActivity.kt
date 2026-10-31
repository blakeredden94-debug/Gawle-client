package com.gawle.client

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 120)
    private val dark = Color.rgb(12, 14, 13)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
            setPadding(22, 35, 22, 20)
        }

        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val title = TextView(this).apply {
            text = "GAWLE CLIENT"
            textSize = 29f
            setTextColor(green)
            typeface = Typeface.DEFAULT_BOLD
        }

        content.addView(title)

        val subtitle = TextView(this).apply {
            text = "MINECRAFT BEDROCK EDITION"
            textSize = 12f
            setTextColor(Color.LTGRAY)
        }

        content.addView(subtitle)

        val status = TextView(this).apply {
            text = "\n● CLIENT READY"
            textSize = 14f
            setTextColor(green)
        }

        content.addView(status)

        val launch = Button(this).apply {
            text = "▶  LAUNCH MINECRAFT"
            textSize = 17f
            setTextColor(Color.BLACK)
            setBackgroundColor(green)

            setOnClickListener {
                val intent = packageManager
                    .getLaunchIntentForPackage(
                        "com.mojang.minecraftpe"
                    )

                if (intent != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Minecraft not found!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        content.addView(launch)

        addSection(content, "CLIENT FEATURES")

        addFeature(content, "Zoom", "In-game zoom support")
        addFeature(content, "Shulker Peek", "Shulker contents preview")
        addFeature(content, "Left-Handed Mode", "Left-handed controls")

        addSection(content, "SETTINGS")

        val settings = TextView(this).apply {
            text = "GAWLE CLIENT  •  VERSION 1.0\n\nMore features coming soon."
            textSize = 13f
            setTextColor(Color.LTGRAY)
            setPadding(0, 15, 0, 30)
        }

        content.addView(settings)

        scroll.addView(content)
        root.addView(scroll)

        setContentView(root)
    }

    private fun addSection(
        layout: LinearLayout,
        name: String
    ) {
        val heading = TextView(this).apply {
            text = "\n$name"
            textSize = 17f
            setTextColor(green)
            typeface = Typeface.DEFAULT_BOLD
        }

        layout.addView(heading)
    }

    private fun addFeature(
        layout: LinearLayout,
        name: String,
        description: String
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 15, 18, 15)
            background = GradientDrawable().apply {
                setColor(Color.rgb(27, 32, 29))
                cornerRadius = 18f
                setStroke(2, green)
            }
        }

        val heading = TextView(this).apply {
            text = name
            textSize = 17f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val info = TextView(this).apply {
            text = "$description\nIN DEVELOPMENT"
            textSize = 12f
            setTextColor(green)
        }

        card.addView(heading)
        card.addView(info)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 10, 0, 5)
        layout.addView(card, params)
    }
}
