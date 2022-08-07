package ui.dendi.finder.app.core

import timber.log.Timber

interface Logger {

    fun log(message: String)
}

class LoggerImpl: Logger {

    override fun log(message: String) {
        Timber.d(message)
    }
}