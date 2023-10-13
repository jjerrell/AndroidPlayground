/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground

import android.app.Application
import timber.log.Timber

class PlaygroundApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }
}

private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // TODO: Release build fault reporting
        return
    }
}
