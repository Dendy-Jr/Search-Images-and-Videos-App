package com.dendi.android.search_images_and_videos_app.core.db.converters

import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.core.base.BaseConverter
import java.util.*

class DateConverter : BaseConverter<Date, Long> {

    @TypeConverter
    override fun toRaw(value: Date?): Long? = value?.time

    @TypeConverter
    override fun fromRaw(raw: Long?): Date? = raw?.let(::Date)

}