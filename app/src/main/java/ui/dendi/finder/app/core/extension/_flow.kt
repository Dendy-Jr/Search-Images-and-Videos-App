package ui.dendi.finder.app.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

fun <T> Fragment.collectWithLifecycle(
    flow: Flow<T>,
    block: suspend (T) -> Unit,
) {
    viewLifecycleOwnerLiveData.observe(this) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flow.collect { block(it) }
                }
            }
        }
    }
}

@ExperimentalCoroutinesApi
fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value ->
        previous.drop(1) + value
    }
}

fun <T1, T2, T3, T4, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    transform: suspend (T1, T2, T3, T4) -> R
): Flow<R> = combine(
    combine(flow, flow2, ::Pair),
    combine(flow3, flow4, ::Pair),
) { t1, t2 ->
    transform(
        t1.first,
        t1.second,
        t2.first,
        t2.second,
    )
}