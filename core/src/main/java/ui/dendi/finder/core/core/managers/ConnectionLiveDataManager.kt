package ui.dendi.finder.core.core.managers

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import ui.dendi.finder.core.core.Logger
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.SocketFactory

@Singleton
class ConnectionLiveDataManager @Inject constructor(
    @ApplicationContext context: Context,
    private val logger: Logger,
) : LiveData<Boolean>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetwork: MutableSet<Network> = HashSet()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private fun checkValidNetwork() {
        postValue(validNetwork.size > 0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            logger.log("onAvailable: $network")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            logger.log("onAvailable: $network, $hasInternetCapability")
            if (hasInternetCapability == true) {
                coroutineScope.launch {
                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    if (hasInternet) {
                        withContext(Dispatchers.Main) {
                            logger.log("onAvailable: adding network. $network")
                            validNetwork.add(network)
                            checkValidNetwork()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            logger.log("onLost: $network")
            validNetwork.remove(network)
            checkValidNetwork()
        }
    }

    object DoesNetworkHaveInternet {

        private lateinit var logger: Logger

        fun execute(socketFactory: SocketFactory): Boolean {
            return try {
                logger.log("PINGING Google...")
                val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
                socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                socket.close()
                logger.log("PING success.")
                true
            } catch (e: IOException) {
                logger.log("No Internet Connection. $e")
                false
            }
        }
    }
}