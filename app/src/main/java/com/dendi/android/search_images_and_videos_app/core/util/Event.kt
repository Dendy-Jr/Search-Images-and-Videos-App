package com.dendi.android.search_images_and_videos_app.core.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Event<T> @Inject constructor(
    value: T
) {

    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }

}