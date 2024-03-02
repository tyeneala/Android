package com.example.coroutines

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val model: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                model.state.collect { uiState -> assignObservers(uiState) }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun assignObservers(uiState: MyUiState) {
        findViewById<Button>(R.id.button0).text = uiState.modeOfFactorialButton.name
        findViewById<TextView>(R.id.textView0).text = getString(R.string.factorial) + uiState.resultFactorial

        findViewById<Button>(R.id.button1).text = uiState.modeOfLogarithmButton.name
        findViewById<TextView>(R.id.textView1).text = getString(R.string.ln) + uiState.resultLn
        findViewById<TextView>(R.id.textView2).text = getString(R.string.lg) + uiState.resultLg

        findViewById<Button>(R.id.button2).text = uiState.modeOfRootButton.name
        findViewById<TextView>(R.id.textView3).text = getString(R.string.sqrt_root) + uiState.resultSqrtRoot
        findViewById<TextView>(R.id.textView4).text = getString(R.string.cube_root) + uiState.resultCubeRoot

        findViewById<Button>(R.id.button3).text = uiState.modeOfPowerButton.name
        findViewById<TextView>(R.id.textView5).text = getString(R.string.squaring) + uiState.resultSquaring
        findViewById<TextView>(R.id.textView6).text = getString(R.string.cubing) + uiState.resultCubing

        findViewById<Button>(R.id.button4).text = uiState.modeOfCommonButton.name
    }

    private fun getNumber() =
        findViewById<EditText>(R.id.editTextNumberDecimal).text.toString()

    fun onClick(view: View) {
        val button = view as Button
        when (button.id) {
            R.id.button0 -> model.doAction(getNumber(), Calc.FACTORIAL)
            R.id.button1 -> model.doAction(getNumber(), Calc.LOGARITHM)
            R.id.button2 -> model.doAction(getNumber(), Calc.ROOT)
            R.id.button3 -> model.doAction(getNumber(), Calc.POWER)
            R.id.button4 -> model.doAction(getNumber(), Calc.ALL)
        }
    }

}