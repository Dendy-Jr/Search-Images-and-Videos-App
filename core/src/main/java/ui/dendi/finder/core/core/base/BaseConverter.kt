package ui.dendi.finder.core.core.base

interface BaseConverter<T : Any, R : Any> {

    fun toRaw(value: T?): R?

    fun fromRaw(raw: R?): T?
}