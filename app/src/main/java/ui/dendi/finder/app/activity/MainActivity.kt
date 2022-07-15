package ui.dendi.finder.app.activity

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import ui.dendi.finder.app.core.managers.ConnectionLiveDataManager
import ui.dendi.finder.app.databinding.ActivityMainBinding
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var connectionLiveDataManager: ConnectionLiveDataManager

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

        Timber.d(savedInstanceState.toString())

        lifecycleScope.launchWhenCreated {
            viewModel.navigation.collect { navDirections ->
                if (navDirections is BackNavDirections) {
                    onBackPressed()
                    return@collect
                }

                viewModel.navController?.navigate(navDirections)
            }
        }

//        Timber.d("is connected? version three: -> ${isInternetAvailable()}")

        connectionLiveDataManager = ConnectionLiveDataManager(this)
        connectionLiveDataManager.observe(this) { isNetworkAvailable ->
            if (isNetworkAvailable) {
//                showToast("Internet connection established")
            } else {
//                showToast("No internet connection")
            }
            Timber.i(isNetworkAvailable.toString())
        }

//        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE).not() &&
//            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).not()
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                ),
//                201
//            )
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
            showToast("Permissions granted")
        }
    }

    // Third version TODO
    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

//    private fun checkPermission(permission: String): Boolean {
//        return ContextCompat.checkSelfPermission(
//            this,
//            permission
//        ) == PackageManager.PERMISSION_GRANTED
//    }
}