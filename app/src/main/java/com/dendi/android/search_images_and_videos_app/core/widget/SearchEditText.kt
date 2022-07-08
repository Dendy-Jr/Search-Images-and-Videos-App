package com.dendi.android.search_images_and_videos_app.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.dendi.android.search_images_and_videos_app.databinding.ItemEditTextBinding


class SearchEditText(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding =
        ItemEditTextBinding.inflate(LayoutInflater.from(context), this, true)

    private var onSearchTextChangedListener: ((String) -> Unit)? = null

    init {
        binding.apply {
            editText.clearFocus()
            ivCancel.setOnClickListener {
                editText.setText("")
            }
            editText.addTextChangedListener {
                onSearchTextChangedListener?.invoke(it.toString().trim())
            }
        }
    }

    fun setSearchTextChangedClickListener(listener: ((String) -> Unit)?) {
        onSearchTextChangedListener = listener
    }
}