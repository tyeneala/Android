package com.example.primenumbers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.commonmodule.Module

class PrimeNumberFragment : Module() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_prime_number, container, false)

        val seasonSpinner = view.findViewById<Spinner>(R.id.spinner)
        seasonSpinner.adapter = ArrayAdapter(view.context,
            android.R.layout.simple_spinner_item,
            Mode.values())

        view?.findViewById<Button>(R.id.button1)?.setOnClickListener { calculate() }

        return view
    }

    private fun calculate() {
        val mode = requireView().findViewById<Spinner>(R.id.spinner).selectedItem as Mode
        val value = (requireView().findViewById<EditText>(R.id.editTextNumber).text.toString())
        outDialog(
            if (value.length != 0)
                convert(PrimeNumbers.calculate(Integer.parseInt(value), mode))
            else getString(R.string.description1)
        )
    }

    fun convert(list: List<Pair<String, String>>): String {
        var result = StringBuffer()
        for(pair in list) {
            result.appendLine("${pair.first} ${pair.second}")
        }
        return result.toString()
    }

}