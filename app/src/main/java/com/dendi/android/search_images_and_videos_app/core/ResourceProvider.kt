package com.dendi.android.search_images_and_videos_app.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourceProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun getString(@StringRes resId: Int) = context.getString(resId)
}