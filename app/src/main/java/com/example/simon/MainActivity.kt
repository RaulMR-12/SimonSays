package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.SoundPool
import android.media.AudioAttributes
import android.view.View
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    private lateinit var redButton: ImageButton
    private lateinit var yellowButton: ImageButton
    private lateinit var blueButton: ImageButton
    private lateinit var greenButton: ImageButton
    private lateinit var startButton: View

    private lateinit var soundPool: SoundPool
    private var soundRed = 0
    private var soundYellow = 0
    private var soundBlue = 0
    private var soundGreen = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redButton = findViewById(R.id.redButton)
        yellowButton = findViewById(R.id.yellowButton)
        blueButton = findViewById(R.id.blueButton)
        greenButton = findViewById(R.id.greenButton)

        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        soundRed = soundPool.load(this, R.raw.red_sound, 1)
        soundYellow = soundPool.load(this, R.raw.yellow_sound, 1)
        soundBlue = soundPool.load(this, R.raw.blue_sound, 1)
        soundGreen = soundPool.load(this, R.raw.green_sound, 1)

        redButton.setOnClickListener { playSound(soundRed) }
        yellowButton.setOnClickListener { playSound(soundYellow) }
        blueButton.setOnClickListener { playSound(soundBlue) }
        greenButton.setOnClickListener { playSound(soundGreen) }
    }

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
}