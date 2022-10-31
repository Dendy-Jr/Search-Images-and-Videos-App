package ui.dendi.finder.favorites_data.videos.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.favorites_data.videos.SmallCache

class SmallConverter: BaseConverter<SmallCache, String> {

    @TypeConverter
    override fun toRaw(value: SmallCache?): String? {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): SmallCache? {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().fromJson(raw, type)
    }
}