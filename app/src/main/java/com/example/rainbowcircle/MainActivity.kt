package com.example.rainbowcircle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.rainbowcircle.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val RED_INDEX = 1
private const val PURPLE_INDEX = 2
private const val BLUE_INDEX = 4
private const val YELLOW_INDEX = 6


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            val restoredUrl = savedInstanceState.getString("imageUrl")
            val word = savedInstanceState.getString("word")
            restoreImage(restoredUrl)
            restoreWord(word)
        }
    }

    private fun restoreWord(word: String?) {
        with(binding.randomText) {
            savedWord = word
            isPrintNothing = true
            invalidate()
        }
    }

    private fun restoreImage(restoredUrl: String?) {
        if (restoredUrl != "") {
            Glide.with(applicationContext)
                .load(restoredUrl)
                .error(R.drawable.baseline_error)
                .into(binding.randomImage)
        }
    }

    override fun onStart() {
        super.onStart()

        binding.resetButton.setOnClickListener {
            clearScreen()
        }

        binding.rainbow.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(3000)
                showContent((it as RainbowCircleView).mainSector)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("imageUrl", url)
        outState.putString("word", binding.randomText.currentWord)
    }

    private fun clearScreen() {
        with(binding) {
            randomImage.setImageResource(R.drawable.ic_launcher_background)
            randomText.isPrintNothing = true
            randomText.invalidate()
        }
    }

    private fun showContent(mainSector: Int) {
        when (mainSector) {
            in listOf(RED_INDEX, YELLOW_INDEX, BLUE_INDEX, PURPLE_INDEX) -> showText()
            else -> showImage()
        }
    }

    private fun showImage() {
        val randomSize = (200..300).random()
        url = "https://picsum.photos/$randomSize"
        Glide.with(applicationContext)
            .load(url)
            .error(R.drawable.baseline_error)
            .into(binding.randomImage)
    }

    private fun showText() {
        with(binding.randomText) {
            isPrintNothing = false
            invalidate()
        }
    }
}