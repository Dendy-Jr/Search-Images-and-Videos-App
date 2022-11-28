package ui.dendi.finder.favorites_presentation.multichoice

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import javax.inject.Inject

class VideosMultiChoiceHandlerImpl @Inject constructor() : MultiChoiceHandler<Video>,
    MultiChoiceState<Video> {

    private val checkedIds = HashSet<Long>()
    private var items: List<Video> = emptyList()
    private var stateFlow = MutableStateFlow(Any())

    override fun setItemsFlow(coroutineScope: CoroutineScope, itemsFlow: Flow<List<Video>>) {
        coroutineScope.launch {
            itemsFlow.collectLatest { list ->
                items = list
                removeDeletedVideo(list)
                notifyUpdate()
            }
        }
    }

    override fun listen(): Flow<MultiChoiceState<Video>> {
        return stateFlow.map { this }
    }

    override fun checkedItems(): Flow<List<Video>> {
        val videos = mutableListOf<Video>()
        checkedIds.forEach { checkedId ->
            val checkedVideo = items.filter { video ->
                video.id == checkedId
            }
            videos.addAll(checkedVideo)
        }
        return flow { emit(videos) }
    }

    override fun isChecked(item: Video): Boolean {
        return checkedIds.contains(item.id)
    }

    override suspend fun toggle(item: Video) {
        if (isChecked(item)) {
            clear(item)
        } else {
            check(item)
        }
    }

    override suspend fun check(item: Video) {
        if (exist(item).not()) return

        checkedIds.add(item.id)
        notifyUpdate()
    }

    override suspend fun clear(item: Video) {
        if (exist(item).not()) return

        checkedIds.remove(item.id)
        notifyUpdate()
    }

    override suspend fun selectAll() {
        checkedIds.addAll(items.map { it.id })
        notifyUpdate()
    }

    private fun exist(item: Video): Boolean {
        return items.any { it.id == item.id }
    }

    override suspend fun clearAll() {
        checkedIds.clear()
        notifyUpdate()
    }

    override val totalCheckedCount: Int
        get() = checkedIds.size

    private fun notifyUpdate() {
        stateFlow.value = Any()
    }

    private fun removeDeletedVideo(videos: List<Video>) {
        val existingIds = HashSet(videos.map { it.id })
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
interface MultiChoiceVideosHandlerModule {

    @ViewModelScoped
    @Binds
    fun binds(impl: VideosMultiChoiceHandlerImpl): MultiChoiceHandler<Video>
}