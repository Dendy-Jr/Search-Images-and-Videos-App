package ui.dendi.finder.core.core

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

interface Logger {

    fun log(message: String)
}

@Singleton
class LoggerImpl @Inject constructor() : Logger {

    //TODO add parameter `place`. For example place: String (place = SearchImagesViewModel)
    override fun log(message: String) {
        Timber.d(message)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface LoggerModule {

    @Binds
    @Singleton
    fun binds(impl: LoggerImpl): Logger
}