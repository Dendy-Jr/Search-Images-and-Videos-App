package ui.dendi.finder.core.core

import timber.log.Timber

interface Logger {

    fun log(message: String)
}

class LoggerImpl: Logger {

    override fun log(message: String) {
        Timber.d(message)
    }
}