package com.example.commonmodule

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

open class Module : Fragment() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.sendLog("Call onCreate()  module: ${this::class.simpleName}")
    }

    override fun onStart() {
        super.onStart()
        Logger.sendLog("Call onStart()  module: ${this::class.simpleName}")
    }

    override fun onResume() {
        super.onResume()
        Logger.sendLog("Call onResume()  module: ${this::class.simpleName}")
    }

    override fun onPause() {
        super.onPause()
        Logger.sendLog("Call onPause()  module: ${this::class.simpleName}")
    }

    override fun onStop() {
        super.onStop()
        Logger.sendLog("Call onStop()  module: ${this::class.simpleName}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.sendLog("Call onDestroy()  module: ${this::class.simpleName}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.sendLog("Call onCreateView() module: ${this::class.simpleName}")

        val view = inflater.inflate(R.layout.fragment_module, container, false)
        navController = NavHostFragment.findNavController(this)

        return view
    }

    fun outDialog(result: String) {
        Logger.sendLog("Call outDialog() module: ${this::class.simpleName}")
        activity?.runOnUiThread {
            val builder = AlertDialog.Builder(requireContext())
            builder.setMessage(result)
                .setNegativeButton("Ok") { dialog, _ -> dialog.cancel() }
            builder.show()
        }
    }
}