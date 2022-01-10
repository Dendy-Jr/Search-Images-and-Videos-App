package com.dendi.android.search_images_and_videos_app.core

import com.dendi.android.search_images_and_videos_app.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * @author Dendy-Jr on 09.01.2022
 * olehvynnytskyi@gmail.com
 */
interface ExceptionMapper : ExceptionFactory<Exception, String> {

    class ExceptionMapperImpl(private val resourceProvider: ResourceProvider) : ExceptionMapper {
        override fun map(exception: Exception): String =
            when (exception) {
                is UnknownHostException -> resourceProvider.provideString(R.string.no_connection_message)
                is HttpException -> resourceProvider.provideString(R.string.not_correctly_entered_word_error)
                is SocketTimeoutException -> resourceProvider.provideString(R.string.timeout_error)
                is NullPointerException -> resourceProvider.provideString(R.string.null_error)
                else -> throw IllegalArgumentException("data -> ExceptionMapper not found ${exception.message}")
            }

    }
}