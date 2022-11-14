package ui.dendi.finder.core.core.base

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import ui.dendi.finder.core.R
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.LoggerImpl
import ui.dendi.finder.core.core.extension.hideKeyboard
import ui.dendi.finder.core.core.managers.ConnectionLiveDataManager
import ui.dendi.finder.core.core.navigation.BackNavDirections
import ui.dendi.finder.core.core.widget.SearchEditText
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel>(
    @LayoutRes contentLayoutId: Int,
) : Fragment(contentLayoutId), ViewModelOwner<VM>, Logger by LoggerImpl() {

    abstract override val viewModel: VM

    open fun onBackPressed(): Boolean = viewModel.navigateBack()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var connectionLiveDataManager: ConnectionLiveDataManager
    private var dialog: AlertDialog? = null

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

        monitoringInternetConnection()
        observeNavigation()
    }

    private fun monitoringInternetConnection() {
        connectionLiveDataManager = ConnectionLiveDataManager(requireContext())
        connectionLiveDataManager.observe(viewLifecycleOwner) { networkConnection ->
            if (networkConnection.not()) {
                showDialog()
            } else {
                dialog?.dismiss()
            }
        }
    }

    private fun showDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.Theme_FinderApp_Dialog)
            .setTitle(getString(R.string.no_internet_connection))
            .setMessage(getString(R.string.check_internet_connection))
            .setIcon(R.drawable.ic_connected_no_internet)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.open_wi_fi_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                startActivity(intent)
            }
            .setNegativeButton(getString(R.string.open_mobile_settings)) { _, _ ->
                val intent = Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS)
                startActivity(intent)
            }
            .setNeutralButton(getString(R.string.close)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog?.show()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    onBackPressedCallback.isEnabled = false
                    //TODO Deprecated
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

    protected fun RecyclerView.setupList(
        adapter: RecyclerView.Adapter<*>,
        editText: SearchEditText,
    ) {
        this.adapter = (adapter as? PagingDataAdapter<*, *>) ?: return
        (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                    editText.clearFocus()
                }
            }
        })

        lifecycleScope.launch {
            val footerAdapter = DefaultLoadStateAdapter { adapter.retry() }
            val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
            this@setupList.adapter = adapterWithLoadState
        }
    }
}