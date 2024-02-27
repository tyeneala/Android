package com.example.commonmodule

import android.util.Log

object Logger  {
  fun sendLog(message: String) {
      Log.d("MyLogger", message)
  }

}