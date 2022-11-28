package ui.dendi.finder.favorites_presentation.multichoice

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import javax.inject.Inject

class ImagesMultiChoiceHandlerImpl @Inject constructor() : MultiChoiceHandler<Image>,
    MultiChoiceState<Image> {

    private val checkedIds = HashSet<Long>()
    private var items: List<Image> = emptyList()
    private var stateFlow = MutableStateFlow(Any())

    override fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<Image>>) {
        coroutineScope.launch {
            itemsFlow.collectLatest { list ->
                items = list
                removeDeletedImages(list)
                notifyUpdate()
            }
        }
    }

    override fun listen(): Flow<MultiChoiceState<Image>> {
        return stateFlow.map { this }
    }

    override fun checkedItems(): Flow<List<Image>> {
        val images = mutableListOf<Image>()
        checkedIds.forEach { checkedId ->
            val checkedImage = items.filter { image ->
                image.id == checkedId
            }
            images.addAll(checkedImage)
        }
        return flow { emit(images) }
    }

    override fun isChecked(item: Image): Boolean {
        return checkedIds.contains(item.id)
    }

    override suspend fun toggle(item: Image) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override suspend fun check(item: Image) {
        if (exists(item).not()) return

        checkedIds.add(item.id)
        notifyUpdate()
    }

    override suspend fun clear(item: Image) {
        if (exists(item).not()) return

        checkedIds.remove(item.id)
        notifyUpdate()
    }

    override suspend fun selectAll() {
        checkedIds.addAll(items.map { it.id })
        notifyUpdate()
    }

    override suspend fun clearAll() {
        checkedIds.clear()
        notifyUpdate()
    }

    override val totalCheckedCount: Int
        get() = checkedIds.size

    private fun exists(item: Image): Boolean {
        return items.any { it.id == item.id }
    }

    private fun notifyUpdate() {
        stateFlow.value = Any()
    }

    private fun removeDeletedImages(images: List<Image>) {
        val existingIds = HashSet(images.map { it.id })
        val iterator = checkedIds.iterator()
        while (iterator.hasNext()) {
            val id = iterator.next()
            if (existingIds.contains(id).not()) {
                iterator.remove()
            }
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface MultiChoiceImagesHandlerModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: ImagesMultiChoiceHandlerImpl): MultiChoiceHandler<Image>
}