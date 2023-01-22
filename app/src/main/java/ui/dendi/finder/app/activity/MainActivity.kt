package ui.dendi.finder.app.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
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

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermission = registerForActivityResult(
        RequestPermission(),
        ::onPermissionResult
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onPermissionResult(granted: Boolean) {
        if (granted) {
            showToast(getString(R.string.permissions_granted))
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE).not()
            ) {
                askUserForOpeningSettings()
            } else {
                showToast(getString(R.string.permission_denied))
            }
        }
    }

    private fun askUserForOpeningSettings() {
        val appSettingsIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        if (packageManager.resolveActivity(
                appSettingsIntent,
                PackageManager.MATCH_DEFAULT_ONLY,
            ) == null
        ) {
            showToast(getString(R.string.permissions_denied_forever))
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.permission_denied)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(R.string.open) { _, _ ->
                    startActivity(appSettingsIntent)
                }
                .create()
                .show()

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