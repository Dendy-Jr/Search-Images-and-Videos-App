package ui.dendi.finder.core.core.multichoice

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MultiChoiceHandler<T : Any> {

    fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<T>>)

    fun listen(): Flow<MultiChoiceState<T>>

    fun checkedItems(): Flow<List<T>>

    suspend fun toggle(item: T)

    suspend fun selectAll()

    suspend fun clearAll()

    suspend fun check(item: T)

    suspend fun clear(item: T)
}