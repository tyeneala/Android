package com.example.thermometer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.commonmodule.Module

class ThermometerFragment : Module() {
    private var season = Season.SUMMER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_thermometer, container, false)
        val seasonSpinner = view.findViewById<Spinner>(R.id.spinner)
        seasonSpinner.adapter = ArrayAdapter(view.context,
            android.R.layout.simple_spinner_item,
            MeasureUnit.values())

        val radioButton1 = view.findViewById<RadioButton>(R.id.radioButton1)
        radioButton1?.toggle()
        radioButton1?.setOnClickListener { season = Season.SUMMER }
        view.findViewById<RadioButton>(R.id.radioButton2).
            setOnClickListener { season = Season.WINTER }

        view.findViewById<Button>(R.id.button)?.setOnClickListener { calculate() }

        return view
    }

    private fun calculate() {
        val unit = requireView().findViewById<Spinner>(R.id.spinner).selectedItem as MeasureUnit
        val str = requireView().findViewById<EditText>(R.id.editTextNumber).text.toString()
        var message = getString(R.string.warning)
        if (str.isNotEmpty()) {
            val value = str.toFloat()
            message = StringBuffer().
                appendLine(getString(R.string.mes1, unit.modified(value).format(), unit)).
                appendLine(getString(R.string.mes2,
                    unit.modified(season.range.start).format(),
                    unit.modified(season.range.endInclusive).format(), unit)).
                appendLine(getAdvice(Thermometer.calculate(value, season, unit))).
                toString()
        }
        outDialog(message)
    }

    fun Float.format() = "%.2f".format(this)

    private fun getAdvice(result: Float): String {
        if (result == 0f)
            return getString(R.string.mes3)
        if (result > 0f)
            return getString(R.string.mes4, result.format())
        return getString(R.string.mes5, kotlin.math.abs(result).format())
    }

}