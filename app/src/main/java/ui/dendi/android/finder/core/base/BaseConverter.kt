package ui.dendi.android.finder.core.base

interface BaseConverter<T : Any, R : Any> {

    fun toRaw(value: T?): R?

    fun fromRaw(raw: R?): T?
}