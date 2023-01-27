package ui.dendi.finder.settings_presentation

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.settings_domain.ItemsPosition
import ui.dendi.finder.settings_presentation.databinding.SettingsFragmentBinding

@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingsViewModel>(R.layout.settings_fragment) {

    private val binding: SettingsFragmentBinding by viewBinding()
    override val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBind()
    }

    private fun onBind() = with(binding) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = radioGroup.findViewById<RadioButton>(checkedId)

            val item = when (radioButton.id) {
                R.id.rbHorizontalSingle -> ItemsPosition.HORIZONTAL_SINGLE
                R.id.rbVerticalSingle -> ItemsPosition.VERTICAL_SINGLE
                R.id.rbHorizontalDouble -> ItemsPosition.HORIZONTAL_DOUBLE
                R.id.rbVerticalDouble -> ItemsPosition.VERTICAL_DOUBLE
                else -> {
                    return@setOnCheckedChangeListener
                }
            }
            viewModel.setItemsPositioning(item)
        }
    }
}