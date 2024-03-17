package com.example.simon

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.SoundPool
import android.media.AudioAttributes
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import kotlin.random.Random

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

    private val sequence = mutableListOf<Int>()
    private var playerInputIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        redButton = findViewById(R.id.redButton)
        yellowButton = findViewById(R.id.yellowButton)
        blueButton = findViewById(R.id.blueButton)
        greenButton = findViewById(R.id.greenButton)
        startButton = findViewById(R.id.startButton)

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

        startButton.setOnClickListener{
            startGame()
        }
    }

    private fun startGame(){
        sequence.clear()
        playerInputIndex = 0
        generateNextSequenceItem()
        playSequence()
    }

    private fun generateNextSequenceItem(){
        val nextColor = Random.nextInt(4)
        sequence.add(nextColor)
    }

    private fun playSequence(){
        val delayMillis = 1000L
        var delay = 0L
        sequence.forEachIndexed { index, color ->
            redButton.postDelayed({
                flashButton(color)
            }, delay)
            delay += delayMillis * (index + 1)
        }
    }

    private fun flashButton(color: Int){
        val buttonToFlash = when (color) {
            0 -> redButton
            1 -> yellowButton
            2 -> blueButton
            3 -> greenButton
            else -> return
        }

        val originalBackgroundColor = buttonToFlash.backgroundTintList
        val darkColor = getDarkColor(color)
        buttonToFlash.backgroundTintList = ColorStateList.valueOf(darkColor)

        val soundId = when (color){
            0 -> soundRed
            1 -> soundYellow
            2 -> soundBlue
            3 -> soundGreen
            else -> return
        }
        playSound(soundId)

        buttonToFlash.postDelayed({
            buttonToFlash.backgroundTintList = originalBackgroundColor
        }, 1000)
    }

    private fun getDarkColor(color: Int): Int {
        return when (color) {
            0 -> ContextCompat.getColor(this, R.color.red_dark)
            1 -> ContextCompat.getColor(this, R.color.yellow_dark)
            2 -> ContextCompat.getColor(this, R.color.blue_dark)
            3 -> ContextCompat.getColor(this, R.color.green_dark)
            else -> ContextCompat.getColor(this, R.color.red_dark)
        }
    }

    override fun onStart() {
        super.onStart()
        playerInputIndex = 0
        redButton.setOnClickListener{ onPlayerInput(0) }
        yellowButton.setOnClickListener{ onPlayerInput(1) }
        blueButton.setOnClickListener{ onPlayerInput(2) }
        greenButton.setOnClickListener{ onPlayerInput(3) }

    }

    private fun onPlayerInput(color: Int){
        if (sequence[playerInputIndex] == color){
            playerInputIndex++
            if (playerInputIndex == sequence.size){
                generateNextSequenceItem()
                playSequence()
                playerInputIndex = 0
            }
        }
        else{
            startGame()
        }

    }

    private fun playSound(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }
}