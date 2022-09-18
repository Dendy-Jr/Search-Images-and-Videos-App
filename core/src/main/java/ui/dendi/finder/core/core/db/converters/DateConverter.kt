package ui.dendi.finder.core.core.db.converters

import androidx.room.TypeConverter
import ui.dendi.finder.core.core.base.BaseConverter
import java.util.*

class DateConverter : BaseConverter<Date, Long> {

    @TypeConverter
    override fun toRaw(value: Date?): Long? = value?.time

    @TypeConverter
    override fun fromRaw(raw: Long?): Date? = raw?.let(::Date)

}