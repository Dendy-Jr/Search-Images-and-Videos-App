package ui.dendi.finder.core.core.extension

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.dateConverter(): String {
    val dateParser = LocalDateTime.parse(this)
    val pattern = "dd.MM.yyyy HH:mm"
    return DateTimeFormatter.ofPattern(pattern)
        .format(dateParser)
}