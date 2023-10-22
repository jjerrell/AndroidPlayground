/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground

import android.app.Application
import dev.jjerrell.android.playground.feature.about.aboutModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import org.koin.core.logger.Logger
import org.koin.core.logger.MESSAGE
import timber.log.Timber

/**
 * Playground application implementation.
 *
 * Configures:
 * - Timber
 *         - Debug
 *         - Release
 * - Koin
 *     - Logging via Timber
 *     - Android context
 *     - Feature module DI
 */
class PlaygroundApplication : Application() {
    /**
     * - Plants the appropriate Timber tree depending on BuildConfig
     * - Configure Koin and initialize modules
     */
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        startKoin {
            setupLogging()
            androidContext(this@PlaygroundApplication)
            modules(aboutModule())
        }
    }

    /** Configure Koin to use Timber for its logging use cases */
    private fun KoinApplication.setupLogging() {
        val koinLogger =
            object : Logger() {
                override fun display(level: Level, msg: MESSAGE) {
                    when (level) {
                        Level.DEBUG -> Timber.d(msg)
                        Level.INFO -> Timber.i(msg)
                        Level.WARNING -> Timber.w(msg)
                        Level.ERROR -> Timber.e(msg)
                        Level.NONE -> Timber.wtf(msg)
                    }
                }
            }
        logger(koinLogger)
    }
}

/** A release-ready crash reporting [Timber.Tree] implementation */
private class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        // Uses default logging implementation. Crashes are sent to Firebase Crashlytics.
        return super.log(priority, tag, message, t)
    }
}
