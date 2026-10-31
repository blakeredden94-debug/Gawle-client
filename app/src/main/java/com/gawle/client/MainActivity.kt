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
    private val dark = Color.rgb(10, 12, 11)
    private val panel = Color.rgb(25, 30, 27)

    private lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 30, 22, 20)
        }

        val scroll = ScrollView(this)
        scroll.addView(content)

        root.addView(scroll, LinearLayout.LayoutParams(
            -1, 0, 1f
        ))

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        listOf("HOME", "FEATURES", "SETTINGS").forEach { tab ->
            val button = Button(this).apply {
                text = tab
                textSize = 11f
                setTextColor(green)
                setBackgroundColor(Color.TRANSPARENT)
                setOnClickListener {
                    showPage(tab)
                }
            }

            nav.addView(button, LinearLayout.LayoutParams(
                0, 65, 1f
            ))
        }

        root.addView(nav)
        setContentView(root)

        showPage("HOME")
    }

    private fun showPage(page: String) {
        content.removeAllViews()

        when (page) {
            "HOME" -> homePage()
            "FEATURES" -> featuresPage()
            "SETTINGS" -> settingsPage()
        }
    }

    private fun addText(
        text: String,
        size: Float,
        color: Int
    ) {
        val view = TextView(this).apply {
            this.text = text
            textSize = size
            setTextColor(color)
        }
        content.addView(view)
    }

    private fun addCard(
        name: String,
        description: String
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 18, 20, 18)
            background = GradientDrawable().apply {
                setColor(panel)
                cornerRadius = 22f
                setStroke(2, green)
            }
        }

        val heading = TextView(this).apply {
            text = name
            textSize = 18f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val info = TextView(this).apply {
            text = description
            textSize = 13f
            setTextColor(green)
        }

        card.addView(heading)
        card.addView(info)

        val params = LinearLayout.LayoutParams(-1, -2)
        params.setMargins(0, 12, 0, 5)
        content.addView(card, params)
    }

    private fun homePage() {
        addText("GAWLE", 38f, green)
        addText("CLIENT", 25f, Color.WHITE)
        addText("MINECRAFT BEDROCK COMPANION", 12f, Color.LTGRAY)

        addText("\n● CLIENT READY", 15f, green)

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

        val params = LinearLayout.LayoutParams(-1, 65)
        params.setMargins(0, 25, 0, 20)
        content.addView(launch, params)

        addText("WELCOME TO GAWLE CLIENT", 18f, Color.WHITE)
        addCard("Your Client", "Your Minecraft companion dashboard.")
        addCard("Features", "Explore available client features.")
        addCard("Settings", "Customise your launcher.")
    }

    private fun featuresPage() {
        addText("FEATURES", 30f, green)
        addText("GAWLE CLIENT MODULES", 12f, Color.LTGRAY)

        addCard(
            "Zoom",
            "In-game zoom support - development planned."
        )

        addCard(
            "Shulker Peek",
            "Preview support - Bedrock API limitations apply."
        )

        addCard(
            "Left-Handed Mode",
            "Left-handed control support - development planned."
        )
    }

    private fun settingsPage() {
        addText("SETTINGS", 30f, green)
        addText("CUSTOMISE YOUR CLIENT", 12f, Color.LTGRAY)

        addCard(
            "App Version",
            "GAWLE CLIENT 1.0"
        )

        addCard(
            "Game",
            "Minecraft Bedrock Edition"
        )

        addCard(
            "Appearance",
            "Black and Emerald Green theme"
        )

        addCard(
            "About",
            "GAWLE CLIENT - Android Minecraft companion."
        )
    }
}
