package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener { calculatetip() }
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculatetip() {
        var tip: Double
        val stringintextfield = binding.costOfServiceEditText.text.toString()
        val cost = stringintextfield.toDoubleOrNull() ?: return
        val selectID = binding.radioGroup.checkedRadioButtonId

        tip = if (selectID == R.id.button1) {
            0.20 * (cost!!)
        } else if (selectID == R.id.button2) {
            0.18 * cost!!
        } else {
            0.15 * cost!!
        }

        val roundup = binding.switch1.isChecked
        if (roundup) {
            tip = ceil(tip)
        }
        if (cost == null) {
            binding.result.text = ""
            return
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.result.text = getString(R.string.tip_amount, formattedTip)

    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}