package com.dendi.android.search_images_and_videos_app.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface ResourceProvider {

    fun provideString(@StringRes id: Int): String
}

class ResourceProviderImpl @Inject constructor(
    private val context: Context,
) : ResourceProvider {
    override fun provideString(id: Int) = context.getString(id)
}

@Module
@InstallIn(SingletonComponent::class)
interface ResourceProviderModule {

    @Binds
    @Singleton
    fun bind(impl: ResourceProviderImpl): ResourceProvider
}