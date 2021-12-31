package com.dendi.android.search_images_and_videos_app.core

import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ItemStore<T> {

    var item: T?

    fun observeItem(): Flow<T?>

    fun updateSafely(updateAction: (T) -> T)
}