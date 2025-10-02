package com.example.emicalculator

import android.content.Intent  // To navigate between activities (INTENT)
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Reference to the Start button from XML
        val startBtn: Button = findViewById(R.id.startBtn)

        startBtn.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)// Create intent to open CalculatorActivity
            startActivity(intent) // Start the activity
        }
    }
}
