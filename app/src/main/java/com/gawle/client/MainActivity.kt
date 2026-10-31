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
    private val dark = Color.rgb(9, 14, 19)
    private val panel = Color.rgb(22, 31, 39)
    private val white = Color.WHITE
    private lateinit var content: LinearLayout

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = dark
        window.navigationBarColor = dark

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(dark)
        }

        content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(25))
        }

        val scroll = ScrollView(this)
        scroll.addView(content)

        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        val nav = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            setBackgroundColor(panel)
        }

        listOf("HOME", "DASHBOARD", "SETTINGS").forEach { tab ->
            val button = Button(this).apply {
                text = tab
                textSize = 10f
                setTextColor(green)
                setBackgroundColor(Color.TRANSPARENT)
                setOnClickListener {
                    showPage(tab)
                }
            }

            nav.addView(button, LinearLayout.LayoutParams(0, dp(65), 1f))
        }

        root.addView(nav)
        setContentView(root)

        showPage("HOME")
    }

    private fun showPage(page: String) {
        content.removeAllViews()

        when (page) {
            "HOME" -> homePage()
            "DASHBOARD" -> dashboardPage()
            "SETTINGS" -> settingsPage()
        }
    }

    private fun addText(
        value: String,
        size: Float,
        color: Int,
        bold: Boolean = false
    ) {
        val view = TextView(this).apply {
            text = value
            textSize = size
            setTextColor(color)
            if (bold) typeface = Typeface.DEFAULT_BOLD
        }

        content.addView(view)
    }

    private fun addGap(height: Int) {
        content.addView(
            LinearLayout(this),
            LinearLayout.LayoutParams(1, dp(height))
        )
    }

    private fun addButton(
        label: String,
        backgroundColor: Int,
        textColor: Int,
        action: () -> Unit
    ) {
        val button = Button(this).apply {
            text = label
            textSize = 15f
            setTextColor(textColor)
            typeface = Typeface.DEFAULT_BOLD
            background = GradientDrawable().apply {
                setColor(backgroundColor)
                cornerRadius = dp(14).toFloat()
            }
            setOnClickListener { action() }
        }

        val params = LinearLayout.LayoutParams(-1, dp(60))
        params.setMargins(0, dp(8), 0, dp(8))
        content.addView(button, params)
    }

    private fun addCard(title: String, description: String) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(18), dp(17), dp(18), dp(17))
            background = GradientDrawable().apply {
                setColor(panel)
                cornerRadius = dp(16).toFloat()
                setStroke(dp(1), Color.rgb(40, 75, 61))
            }
        }

        val heading = TextView(this).apply {
            text = title
            textSize = 18f
            setTextColor(white)
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
        params.setMargins(0, dp(8), 0, dp(8))
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
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun homePage() {
        addText("GAWLE", 38f, green, true)
        addText("CLIENT", 27f, white, true)
        addText("THE ULTIMATE BEDROCK COMPANION", 11f, Color.LTGRAY)

        addGap(25)

        addCard(
            "CLIENT STATUS",
            "● READY TO LAUNCH"
        )

        addGap(15)

        addButton(
            "▶  PLAY MINECRAFT",
            green,
            Color.BLACK
        ) {
            launchMinecraft()
        }

        addButton(
            "OPEN CLIENT DASHBOARD",
            panel,
            green
        ) {
            showPage("DASHBOARD")
        }

        addGap(20)

        addText("WELCOME BACK", 22f, white, true)
        addGap(8)

        addText(
            "Your Minecraft experience starts here.",
            14f,
            Color.LTGRAY
        )

        addGap(12)

        addCard(
            "YOUR CLIENT",
            "Explore your GAWLE CLIENT dashboard."
        )

        addCard(
            "CUSTOM FEATURES",
            "Discover the modules being developed."
        )
    }

    private fun dashboardPage() {
        addText("CLIENT", 16f, Color.LTGRAY, true)
        addText("DASHBOARD", 32f, green, true)

        addGap(10)

        addCard(
            "ZOOM",
            "In-game zoom support. Development planned."
        )

        addCard(
            "SHULKER PEEK",
            "Shulker contents preview. Add-on integration required."
        )

        addCard(
            "LEFT-HANDED MODE",
            "Left-handed controls. Development planned."
        )

        addCard(
            "CLIENT INFORMATION",
            "GAWLE CLIENT Android companion."
        )

        addGap(15)

        addButton(
            "▶  LAUNCH MINECRAFT",
            green,
            Color.BLACK
        ) {
            launchMinecraft()
        }
    }

    private fun settingsPage() {
        addText("SETTINGS", 32f, green, true)
        addText("CUSTOMISE YOUR CLIENT", 12f, Color.LTGRAY)

        addGap(20)

        addCard("VERSION", "GAWLE CLIENT 2.0")
        addCard("GAME", "Minecraft Bedrock Edition")
        addCard("THEME", "Dark Emerald Gaming Theme")
        addCard("ABOUT", "Made for Minecraft Bedrock players.")
    }
}
