package ui.dendi.finder.settings_presentation

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.core.core.theme.applyTextColorGradient
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
        tvPositioningItems.applyTextColorGradient()
        tvImageScreens.applyTextColorGradient()
        tvVideoScreens.applyTextColorGradient()

        radioGroupImage.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = radioGroupImage.findViewById<RadioButton>(checkedId)

            val item = when (radioButton.id) {
                R.id.rbOneColumnImage -> ImagesColumnType.ONE_COLUMN
                R.id.rbTwoColumnsImage -> ImagesColumnType.TWO_COLUMNS
                R.id.rbThreeColumnsImage -> ImagesColumnType.THREE_COLUMNS
                R.id.rbFourColumnsImage -> ImagesColumnType.FOUR_COLUMNS
                else -> return@setOnCheckedChangeListener
            }
            viewModel.setItemsPositioning(item)
        }

        radioGroupVideo.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = radioGroupVideo.findViewById<RadioButton>(checkedId)

            val item = when (radioButton.id) {
                R.id.rbOneColumnVideo -> VideosColumnType.ONE_COLUMN
                R.id.rbTwoColumnsVideo -> VideosColumnType.TWO_COLUMNS
                else -> return@setOnCheckedChangeListener
            }
            viewModel.setVideosPositioning(item)
        }
    }
}