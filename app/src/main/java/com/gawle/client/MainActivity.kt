package com.gawle.client

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import android.content.SharedPreferences

class MainActivity : Activity() {

    private val green = Color.rgb(0, 255, 120)
    private val dark = Color.rgb(9, 14, 19)
    private val panel = Color.rgb(22, 31, 39)

    private lateinit var content: LinearLayout
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("gawle_settings", MODE_PRIVATE)

        window.statusBarColor = dark
        window.navigationBarColor = dark

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(22, 25, 22, 25)
        }

        val scroll = ScrollView(this)
        scroll.addView(content)

        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        listOf("HOME", "FEATURES", "SETTINGS").forEach { tab ->
            val button = Button(this).apply {
                text = tab
                textSize = 10f
                setTextColor(green)
                setBackgroundColor(Color.TRANSPARENT)
                setOnClickListener { showPage(tab) }
            }

            nav.addView(button, LinearLayout.LayoutParams(0, 65, 1f))
        }

        root.addView(nav)
        setContentView(root)

        showPage("HOME")

        if (prefs.getBoolean("auto_launch", false)) {
            launchMinecraft()
        }
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
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ) {
        val text = TextView(this).apply {
            this.text = value
            textSize = size
            setTextColor(color)
            if (bold) typeface = Typeface.DEFAULT_BOLD
        }

        content.addView(text)
    }

    private fun addGap(height: Int) {
        content.addView(
            LinearLayout(this),
            LinearLayout.LayoutParams(1, height)
        )
    }

    private fun addCard(title: String, description: String) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(20, 18, 20, 18)

            background = GradientDrawable().apply {
                setColor(panel)
                cornerRadius = 20f
                setStroke(2, Color.rgb(35, 80, 58))
            }
        }

        val heading = TextView(this).apply {
            text = title
            textSize = 18f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val info = TextView(this).apply {
            text = description
            textSize = 13f
            setTextColor(Color.LTGRAY)
        }

        card.addView(heading)
        card.addView(info)

        val params = LinearLayout.LayoutParams(-1, -2)
        params.setMargins(0, 10, 0, 10)
        content.addView(card, params)
    }

    private fun addSwitch(
        title: String,
        description: String,
        key: String,
        defaultValue: Boolean = false
    ) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(18, 15, 18, 15)

            background = GradientDrawable().apply {
                setColor(panel)
                cornerRadius = 18f
                setStroke(2, Color.rgb(35, 80, 58))
            }
        }

        val heading = TextView(this).apply {
            text = title
            textSize = 17f
            setTextColor(Color.WHITE)
            typeface = Typeface.DEFAULT_BOLD
        }

        val info = TextView(this).apply {
            text = description
            textSize = 12f
            setTextColor(Color.LTGRAY)
        }

        val toggle = Switch(this).apply {
            text = "Enable"
            textSize = 14f
            setTextColor(green)
            isChecked = prefs.getBoolean(key, defaultValue)

            setOnCheckedChangeListener { _, checked ->
                prefs.edit().putBoolean(key, checked).apply()

                Toast.makeText(
                    this@MainActivity,
                    if (checked) "$title enabled" else "$title disabled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        card.addView(heading)
        card.addView(info)
        card.addView(toggle)

        val params = LinearLayout.LayoutParams(-1, -2)
        params.setMargins(0, 9, 0, 9)
        content.addView(card, params)
    }

    private fun launchMinecraft() {
        val intent = packageManager.getLaunchIntentForPackage(
            "com.mojang.minecraftpe"
        )

        if (intent != null) {
            startActivity(intent)
        } else {
            Toast.makeText(
                this,
                "Minecraft not found!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun homePage() {
        addText("GAWLE", 40f, green, true)
        addText("CLIENT 3.0", 25f, Color.WHITE, true)

        addText(
            "MINECRAFT BEDROCK COMPANION",
            12f,
            Color.LTGRAY
        )

        addGap(25)

        addCard(
            "CLIENT STATUS",
            "● GAWLE CLIENT READY"
        )

        val launch = Button(this).apply {
            text = "▶  LAUNCH MINECRAFT"
            textSize = 17f
            setTextColor(Color.BLACK)
            typeface = Typeface.DEFAULT_BOLD

            background = GradientDrawable().apply {
                setColor(green)
                cornerRadius = 18f
            }

            setOnClickListener {
                launchMinecraft()
            }
        }

        val params = LinearLayout.LayoutParams(-1, 65)
        params.setMargins(0, 20, 0, 20)
        content.addView(launch, params)

        addButtonDashboard()
    }

    private fun addButtonDashboard() {
        val button = Button(this).apply {
            text = "OPEN CLIENT FEATURES"
            setTextColor(green)
            setBackgroundColor(panel)
            setOnClickListener {
                showPage("FEATURES")
            }
        }

        content.addView(
            button,
            LinearLayout.LayoutParams(-1, 60)
        )
    }

    private fun featuresPage() {
        addText("CLIENT FEATURES", 29f, green, true)
        addText("MANAGE YOUR MODULES", 12f, Color.LTGRAY)

        addGap(15)

        addSwitch(
            "Zoom",
            "Zoom integration - requires Minecraft support.",
            "zoom"
        )

        addSwitch(
            "Shulker Peek",
            "Shulker preview integration - requires Minecraft support.",
            "shulker"
        )

        addSwitch(
            "Left-handed Mode",
            "Left-handed preference - requires Minecraft support.",
            "left_handed"
        )
    }

    private fun settingsPage() {
        addText("SETTINGS", 32f, green, true)
        addText("CUSTOMISE GAWLE CLIENT", 12f, Color.LTGRAY)

        addGap(15)

        addSwitch(
            "Auto Launch",
            "Automatically open Minecraft when GAWLE starts.",
            "auto_launch"
        )

        addCard(
            "VERSION",
            "GAWLE CLIENT 3.0"
        )

        addCard(
            "GAME",
            "Minecraft Bedrock Edition"
        )

        addCard(
            "THEME",
            "Dark Emerald Gaming Theme"
        )

        addCard(
            "ABOUT",
            "GAWLE CLIENT Android Companion"
        )
    }
}
