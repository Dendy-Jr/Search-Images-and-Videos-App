package ui.dendi.finder.app.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import ui.dendi.finder.app.databinding.ItemEditTextBinding

class SearchEditText(
    context: Context, attributeSet: AttributeSet? = null,
) : ConstraintLayout(context, attributeSet) {

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
                setCancelButtonVisibility(it.toString())
            }
        }
    }

    fun setSearchTextChangedClickListener(listener: ((String) -> Unit)?) {
        onSearchTextChangedListener = listener
    }

    fun setQuery(query: String) = with(binding) {
        if (editText.text.isNotEmpty()) return@with
        editText.setText(query)
    }

    private fun setCancelButtonVisibility(text: String) {
        binding.ivCancel.isVisible = text.isNotEmpty()
    }
}