package ui.dendi.finder.app.core.base

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface BaseRepository<T : Any> {

    fun getPagedItems(
        query: String,
        type: String? = null,
        category: String? = null,
        orientation: String? = null,
        colors: String? = null,
    ): Flow<PagingData<T>>

    fun getItems(): Flow<List<T>>

    suspend fun insertAllItems(items: List<T>)

    suspend fun insertItem(item: T)

    suspend fun deleteItem(item: T)

    suspend fun deleteAllItems()
}