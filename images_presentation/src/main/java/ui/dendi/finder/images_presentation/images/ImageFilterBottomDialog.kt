package ui.dendi.finder.images_presentation.images

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
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.BottomDialogImageFilterBinding

@AndroidEntryPoint
class ImageFilterBottomDialog : BottomSheetDialogFragment() {

    private val viewModel: ImageFilterViewModel by viewModels()
    private val binding: BottomDialogImageFilterBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_dialog_image_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBind()
    }

    private fun onBind() = with(binding) {
        rgType.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setType(radioButton.text.toString())
        }

        rgCategory.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setCategory(radioButton.text.toString())
        }

        rgOrientation.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setOrientation(radioButton.text.toString())
        }

        rgColor.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setColors(radioButton.text.toString())
        }

        btnClose.setOnClickListener {
            this@ImageFilterBottomDialog.dismiss()
        }

        btnReset.setOnClickListener {
            viewModel.clearImagesFilter()
            requireContext().showToast(getString(R.string.filtering_has_been_reset))
        }
    }
}