package ui.dendi.finder.app.core.base

interface BaseConverter<T : Any, R : Any> {

    fun toRaw(value: T?): R?

    fun fromRaw(raw: R?): T?
}