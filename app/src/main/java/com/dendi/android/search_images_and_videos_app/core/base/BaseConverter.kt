package com.dendi.android.search_images_and_videos_app.core.base

interface BaseConverter<T : Any, R : Any> {

    fun toRaw(value: T?): R?

    fun fromRaw(raw: R?): T?
}