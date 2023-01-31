package ui.dendi.finder.settings_presentation

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.models.ListColumnType
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
                R.id.rbOneColumn -> ListColumnType.ONE_COLUMN
                R.id.rbTwoColumns -> ListColumnType.TWO_COLUMNS
                R.id.rbThreeColumns -> ListColumnType.THREE_COLUMNS
                R.id.rbFourColumns -> ListColumnType.FOUR_COLUMNS
                else -> {
                    return@setOnCheckedChangeListener
                }
            }
            viewModel.setItemsPositioning(item)
        }
    }
}