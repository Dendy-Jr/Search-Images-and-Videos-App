package ui.dendi.finder.core.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface Logger {

    fun log(message: String, tag: String)
}

@Singleton
class LoggerImpl @Inject constructor() : Logger {

    override fun log(message: String, tag: String) {
        Timber.tag(tag).d("message=$message")
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    @Binds
    @Singleton
    fun binds(impl: LoggerImpl): Logger
}