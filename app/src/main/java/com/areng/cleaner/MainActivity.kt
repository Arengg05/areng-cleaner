package com.areng.cleaner

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var tvCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPermission = findViewById<Button>(R.id.btnPermission)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnPause = findViewById<Button>(R.id.btnPause)
        val btnOpenTikTok = findViewById<Button>(R.id.btnOpenTikTok)
        tvStatus = findViewById(R.id.tvStatus)
        tvCounter = findViewById(R.id.tvCounter)

        btnPermission.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        btnStart.setOnClickListener {
            TikTokCleanerService.isRunning = true
            tvStatus.text = "Status: Berjalan..."
        }

        btnPause.setOnClickListener {
            TikTokCleanerService.isRunning = false
            tvStatus.text = "Status: Dijeda"
        }

        btnOpenTikTok.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.zhiliaoapp.musically")
                ?: packageManager.getLaunchIntentForPackage("com.ss.android.ugc.trill")
            
            if (launchIntent != null) {
                startActivity(launchIntent)
            } else {
                tvStatus.text = "Status: TikTok tidak ditemukan!"
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tvCounter.text = "Repost Dihapus: ${TikTokCleanerService.deletedCount}"
    }
}
