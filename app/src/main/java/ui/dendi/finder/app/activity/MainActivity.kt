package ui.dendi.finder.app.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.app.R
import ui.dendi.finder.core.core.base.BaseActivity
import ui.dendi.finder.core.core.navigation.BackNavDirections
import ui.dendi.finder.core.core.extension.showToast

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

        lifecycleScope.launchWhenCreated {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    onBackPressed()
                    return@collect
                }

                viewModel.navController?.navigate(navDirections)
            }
        }
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

    private fun onPermissionsResult(grandResults: Map<String, Boolean>) {
        if (grandResults.entries.all { it.value }) {
            showToast(getString(R.string.permissions_granted))
        }
    }
}