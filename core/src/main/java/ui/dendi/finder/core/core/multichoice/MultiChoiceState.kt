package ui.dendi.finder.core.core.multichoice

interface MultiChoiceState<T> {

    fun isChecked(item: T): Boolean
    val totalCheckedCount: Int
}