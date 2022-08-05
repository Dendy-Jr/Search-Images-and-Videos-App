package ui.dendi.finder.app.activity

import android.Manifest
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ui.dendi.finder.app.R
import ui.dendi.finder.app.navigation.BackNavDirections
import ui.dendi.finder.app.core.extension.showToast
import ui.dendi.finder.app.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

//    @Inject
//    lateinit var connectionLiveDataManager: ConnectionLiveDataManager

    private val binding: ActivityMainBinding by viewBinding()

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
        ::onPermissionsResult
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        //TODO Chang it in the future
//        connectionLiveDataManager = ConnectionLiveDataManager(this)
//        connectionLiveDataManager.observe(this) { isNetworkAvailable ->
//            if (isNetworkAvailable) {
//                binding.ivConnection.setImageResource(R.drawable.ic_wifi_on)
////                binding.root.customSnackbar(
////                    resId = R.string.internet_connection_established,
////                    drawableId = R.drawable.ic_wifi_on
////                )
//            } else {
//                binding.ivConnection.setImageResource(R.drawable.ic_wifi_off)
//
////                binding.root.customSnackbar(
////                    resId = R.string.no_internet_connection,
////                    drawableId = R.drawable.ic_wifi_off,
////                )
//            }
//        }
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