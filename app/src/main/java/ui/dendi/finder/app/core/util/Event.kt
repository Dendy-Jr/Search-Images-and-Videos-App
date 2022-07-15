package ui.dendi.finder.app.core.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Event<T> @Inject constructor(
    value: T
) {

    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }

}