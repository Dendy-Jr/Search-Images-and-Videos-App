package ui.dendi.finder.app.fragment

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.dendi.finder.app.R
import ui.dendi.finder.app.activity.MainActivity
import ui.dendi.finder.app.core.base.BaseFragment
import ui.dendi.finder.app.core.base.EmptyViewModel
import ui.dendi.finder.app.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : BaseFragment<EmptyViewModel>(R.layout.fragment_splash) {

    private val binding: FragmentSplashBinding by viewBinding()
    override val viewModel: EmptyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() {
        playAnimation()
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            launchMainScreen()
        }
    }

    private fun launchMainScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun playAnimation() = with(binding) {
        lottie.repeatMode = ValueAnimator.INFINITE
        lottie.playAnimation()
    }
}