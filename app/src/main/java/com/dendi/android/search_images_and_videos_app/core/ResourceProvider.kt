package com.dendi.android.search_images_and_videos_app.core

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager

/**
 * @author Dendy-Jr on 22.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ResourceProvider {

    fun getConnectivityManager(): ConnectivityManager

    fun provideSharedPreferences(): SharedPreferences

    class ResourceProviderImpl(private val context: Context) : ResourceProvider {
        override fun getConnectivityManager() =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        override fun provideSharedPreferences(): SharedPreferences {
           return context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        }
    }

    private companion object {
        const val SHARED_NAME = "shared_name"
    }
}