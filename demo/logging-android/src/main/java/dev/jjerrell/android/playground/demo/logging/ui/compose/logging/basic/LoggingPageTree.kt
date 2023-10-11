/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

@SuppressLint("LogNotTimber")
internal class LoggingPageTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.VERBOSE -> Log.v(tag, message, t)
            Log.DEBUG -> Log.d(tag, message, t)
            Log.INFO -> Log.i(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
            Log.ERROR -> Log.e(tag, message, t)
            else -> Log.wtf(tag, message, t)
        }
    }
}
