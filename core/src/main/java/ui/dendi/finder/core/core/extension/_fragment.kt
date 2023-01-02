package ui.dendi.finder.core.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Create instance of [ViewModel] to attach to the current fragment's lifecycle.
 * Use [parentViewModel] to get the same instance of this [ViewModel] os child fragment.
 */

inline fun <reified VM : ViewModel> Fragment.childViewModel(): VM = viewModels<VM>().value

/**
 * Attach [ViewModel] to lifecycle of parent fragment (ignore [NavHostFragment])
 */
inline fun <reified VM : ViewModel> Fragment.parentViewModel(): Lazy<VM> =
    viewModels(
        ownerProducer = {
            var fragment = parentFragment
            while (fragment is NavHostFragment) {
                fragment = fragment.parentFragment
            }
            fragment ?: this
        }
    )

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