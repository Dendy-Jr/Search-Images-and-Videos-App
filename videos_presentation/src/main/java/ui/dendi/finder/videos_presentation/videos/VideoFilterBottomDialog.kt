package ui.dendi.finder.videos_presentation.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.BottomDialogVideoFilterBinding

@AndroidEntryPoint
class VideoFilterBottomDialog : BottomSheetDialogFragment() {

    private val viewModel: VideoFilterViewModel by viewModels()
    private val binding: BottomDialogVideoFilterBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_video_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBind()
    }

    private fun onBind()= with(binding) {
        rgType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setType(radioButton.text.toString())
        }

        rgCategory.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setCategory(radioButton.text.toString())
        }

        rgOrder.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setOrder(radioButton.text.toString())
        }

        btnClose.setOnClickListener {
            this@VideoFilterBottomDialog.dismiss()
        }

        btnReset.setOnClickListener {
            viewModel.clearVideosFilter()
            requireContext().showToast(getString(R.string.filtering_has_been_reset))
        }
    }
}