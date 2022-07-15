package ui.dendi.finder.app.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.checkNetworkConnection(): Boolean {
    val connectivityManager = getSystemService(
        ConnectivityManager::class.java
    ) as ConnectivityManager
    val currentNetwork = connectivityManager.activeNetwork
    return currentNetwork != null
}