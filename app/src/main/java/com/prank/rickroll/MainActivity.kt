package com.prank.rickroll

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.prank.rickroll.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var isPrankActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Keep screen alive
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setupDecoyUi()
        setupListeners()
        setupBackPressHandling()
    }

    private fun setupDecoyUi() {
        val pulseAnimation = ScaleAnimation(
            0.9f, 1.15f,
            0.9f, 1.15f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1000
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.ivScanPulse.startAnimation(pulseAnimation)
    }

    private fun setupListeners() {
        // Main CTA button triggers the inescapable prank
        binding.btnStartScan.setOnClickListener {
            triggerPrank()
        }
    }

    /**
     * Completely disables the back button when the prank is active!
     * The victim cannot back out of the app.
     */
    private fun setupBackPressHandling() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isPrankActive) {
                    // Back button is completely ignored!
                    maximizeMediaVolume()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    /**
     * Intercepts hardware volume down and mute keys!
     * If the user tries to lower volume, immediately set it back to MAX!
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (isPrankActive) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_DOWN,
                KeyEvent.KEYCODE_VOLUME_MUTE,
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    maximizeMediaVolume()
                    return true // Consume key event, prevent lowering volume
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Triggers the continuous, inescapable audio prank.
     */
    private fun triggerPrank() {
        isPrankActive = true

        // 1. Hide decoy & show trapped prank screen
        binding.layoutDecoy.visibility = View.GONE
        binding.layoutPrankResult.visibility = View.VISIBLE

        // 2. High energy dance animation
        val danceAnim = ScaleAnimation(
            0.9f, 1.25f,
            0.9f, 1.25f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 350
            repeatMode = Animation.REVERSE
            repeatCount = Animation.INFINITE
        }
        binding.tvPrankEmoji.startAnimation(danceAnim)

        // 3. Maximize volume immediately
        maximizeMediaVolume()

        // 4. Start infinite audio loop
        startAudioPlayback()
    }

    private fun maximizeMediaVolume() {
        try {
            val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)
        } catch (_: Exception) {}
    }

    private fun startAudioPlayback() {
        try {
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, R.raw.rickroll).apply {
                isLooping = true // INFINITE LOOP - Never stops!
                start()
            }
        } catch (_: Exception) {}
    }

    override fun onResume() {
        super.onResume()
        if (isPrankActive) {
            maximizeMediaVolume()
            if (mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
