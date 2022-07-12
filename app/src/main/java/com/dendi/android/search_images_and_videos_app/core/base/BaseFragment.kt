package com.dendi.android.search_images_and_videos_app.core.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.app.navigation.BackNavDirections
import kotlinx.coroutines.launch

abstract class BaseFragment<VM : BaseViewModel>(
    @LayoutRes contentLayoutId: Int,
) : Fragment(contentLayoutId), ViewModelOwner<VM> {

    abstract override val viewModel: VM

    open fun onBackPressed(): Boolean = viewModel.navigateBack()

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (onBackPressed()) {
                    onBackPressedCallback.isEnabled = false
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeNavigation()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    onBackPressedCallback.isEnabled = false
                    requireActivity().onBackPressed()
                    return@collect
                }

                try {
                    findNavController().navigate(navDirections)
                } catch (error: Throwable) {
                    requireActivity().findNavController(R.id.navContainer).navigate(navDirections)
                }
            }
        }
    }

    protected fun shareItem(userName: String, url: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_TEXT,
                "${userName}\n${url}",
            )
            type = "text/plain"
        }
        val sharedIntent = Intent.createChooser(sendIntent, userName)
        startActivity(sharedIntent)
    }
}