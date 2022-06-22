package com.dendi.android.search_images_and_videos_app.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface BaseRepository<T : Any> {

    fun search(query: String): Flow<PagingData<T>>

    fun getItems(): Flow<List<T>>

    suspend fun insertAllItems(items: List<T>)

    suspend fun insertItem(item: T)

    suspend fun deleteItem(item: T)

    suspend fun deleteAllItems()
}