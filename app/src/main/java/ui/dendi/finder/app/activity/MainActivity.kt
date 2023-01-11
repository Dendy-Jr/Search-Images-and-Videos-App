package ui.dendi.finder.app.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.app.R
import ui.dendi.finder.core.core.base.BaseActivity
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.core.core.navigation.BackNavDirections

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onPermissionsResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        requestPermission.launch(
            arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        )

        enableBackPressed()
        observeNavigation()
    }

    override fun onStart() {
        super.onStart()
        viewModel.navController = findNavController(R.id.navContainer)
    }

    override fun onStop() {
        super.onStop()
        viewModel.navController = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.onActive()
    }

    private fun enableBackPressed() {
        onBackPressed(true) {
            val isMainFragment =
                viewModel.navController?.currentDestination?.id == R.id.mainFragment
            if (isMainFragment) {
                showAppClosingDialog()
            } else {
                viewModel.navController?.popBackStack()
            }
        }
    }

    private fun observeNavigation() {
        lifecycleScope.launchWhenResumed {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    viewModel.navController?.popBackStack()
                    return@collect
                }

                viewModel.navController?.navigate(navDirections)
            }
        }
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.close_the_app))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> finish() }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    private fun onPermissionsResult(grandResults: Map<String, Boolean>) {
        if (grandResults.entries.all { it.value }) {
            showToast(getString(R.string.permissions_granted))
        }
    }

    private fun AppCompatActivity.onBackPressed(isEnabled: Boolean, callback: () -> Unit) {
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(isEnabled) {
                override fun handleOnBackPressed() {
                    callback()
                }
            })
    }
}