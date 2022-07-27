package ui.dendi.finder.app.feature_images.presentation.images

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ui.dendi.finder.app.R
import ui.dendi.finder.app.databinding.BottomDialogImageFilterBinding

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

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setType(radioButton.text.toString())
            Timber.d("radioButton -> ${radioButton.text}")
        }

        binding.rgCategory.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            viewModel.setCategory(radioButton.text.toString())
            Timber.d("radioButton -> ${radioButton.text}")
        }
    }
}