package com.dendi.android.search_images_and_videos_app.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
class HeapItemStore<T : Any>(initialValue: T? = null) : ItemStore<T> {

    private val itemState = MutableStateFlow<T?>(initialValue)

    override var item: T?
        get() = itemState.value
        set(newValue) {
            itemState.value = newValue
        }

    override fun observeItem(): Flow<T?> = itemState

    override fun updateSafely(updateAction: (T) -> T) =
        itemState.update { prevValue ->
            if (prevValue != null) {
                updateAction(prevValue)
            } else {
                prevValue
            }
        }
}