package com.example.smartcalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.commonmodule.Module

class MenuFragment : Module() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        view.findViewById<Button>(R.id.button1).setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_circles) }
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_primeNumberFragment) }
        view.findViewById<Button>(R.id.button3).setOnClickListener {
            navController.navigate(R.id.action_menuFragment_to_thermometerFragment) }

        return view

    }

}