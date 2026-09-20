package com.rakib.ai

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // মূল UI প্রোগ্রামাটিকভাবে তৈরি করা
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
            setBackgroundColor(android.graphics.Color.parseColor("#0F172A"))
        }

        val title = TextView(this).apply {
            text = "CARROM AI ASSISTANT VIP"
            textSize = 22f
            setTextColor(android.graphics.Color.parseColor("#38BDF8"))
            gravity = android.view.Gravity.CENTER
            setPadding(0, 20, 0, 40)
        }

        val keyInput = EditText(this).apply {
            hint = "Enter VIP License Key"
            setHintTextColor(android.graphics.Color.LTGRAY)
            setTextColor(android.graphics.Color.WHITE)
            setBackgroundColor(android.graphics.Color.parseColor("#1E293B"))
            setPadding(30, 30, 30, 30)
        }

        val verifyBtn = Button(this).apply {
            text = "Activate VIP Key"
            setBackgroundColor(android.graphics.Color.parseColor("#0284C7"))
            setTextColor(android.graphics.Color.WHITE)
        }

        val startBtn = Button(this).apply {
            text = "Enable Auto-Swipe Permission"
            setBackgroundColor(android.graphics.Color.parseColor("#10B981"))
            setTextColor(android.graphics.Color.WHITE)
        }

        verifyBtn.setOnClickListener {
            val key = keyInput.text.toString().trim()
            if (key.isEmpty()) {
                Toast.makeText(this, "Please enter a key", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            dialog.verifyLicense(this, key, object : dialog.LicenseCallback {
                override fun onSuccess(message: String) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(error: String) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, error, Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        startBtn.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            Toast.makeText(this, "Turn ON 'Carrom AI Assistant' in Accessibility", Toast.LENGTH_LONG).show()
        }

        layout.addView(title)
        layout.addView(keyInput)
        layout.addView(verifyBtn)
        layout.addView(startBtn)

        setContentView(layout)
    }
}
