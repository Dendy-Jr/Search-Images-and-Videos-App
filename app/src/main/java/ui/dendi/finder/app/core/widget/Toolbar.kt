package ui.dendi.finder.app.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnNextLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ui.dendi.finder.app.core.base.ViewModelOwner
import ui.dendi.finder.app.core.extension.loadImageOriginal
import ui.dendi.finder.app.databinding.ItemToolbarBinding

class Toolbar(
    context: Context, attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet) {

    private val binding = ItemToolbarBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        doOnNextLayout {
            onBackClicked {
                (FragmentManager.findFragment<Fragment>(this) as? ViewModelOwner<*>)
                    ?.viewModel?.navigateBack()
            }
        }
    }

    fun setTitle(title: String) {
        binding.tvUser.text = title
    }

    fun setUserImage(imageURL: String) {
        binding.userImage.loadImageOriginal(imageURL)
    }

    private fun onBackClicked(listener: () -> Unit) {
        binding.btnBack.setOnClickListener { listener() }
    }
}