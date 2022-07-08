package com.dendi.android.search_images_and_videos_app.app.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.app.navigation.Navigator
import com.dendi.android.search_images_and_videos_app.app.navigation.setupWithNavController
import com.dendi.android.search_images_and_videos_app.core.extension.showToast
import com.dendi.android.search_images_and_videos_app.core.managers.ConnectionLiveDataManager
import com.dendi.android.search_images_and_videos_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var connectionLiveDataManager: ConnectionLiveDataManager

    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        val navController = binding.fragmentContainer.getFragment<NavHostFragment>().navController
        navigator.setupWithNavController(this, navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController)

        Timber.d("is connected? version three: -> ${isInternetAvailable()}")

        connectionLiveDataManager = ConnectionLiveDataManager(this)
        connectionLiveDataManager.observe(this) { isNetworkAvailable ->
            if (isNetworkAvailable) {
                showToast("Internet connection established")
            } else {
                showToast("No internet connection")
            }
            Timber.i(isNetworkAvailable.toString())
        }

        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE).not() &&
            checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).not()
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ),
                201
            )
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

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}