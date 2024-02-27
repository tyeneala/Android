package com.example.circles

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.commonmodule.Module

class Circles : Module() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_circles_ui, container, false)

        view?.findViewById<Button>(R.id.button)?.setOnClickListener { calculate() }
        return view
    }

    private fun calculate() {
        val circle1 = Circle("${getValueInField(R.id.editTextNumber1)} " +
                "${getValueInField(R.id.editTextNumber2)} " +
                "${getValueInField(R.id.editTextNumber3)}")
        val circle2 = Circle("${getValueInField(R.id.editTextNumber5)} " +
                "${getValueInField(R.id.editTextNumber4)} " +
                "${getValueInField(R.id.editTextNumber6)}")
        val array = resources.getStringArray(R.array.circles_relation)
        outDialog(
            if (circle1 == null || circle2 == null)
                array[0] else array[circle1.getRelation(circle2).ordinal]
        )
    }

    fun getValueInField(id: Int) = requireView().findViewById<EditText>(id)?.text
}