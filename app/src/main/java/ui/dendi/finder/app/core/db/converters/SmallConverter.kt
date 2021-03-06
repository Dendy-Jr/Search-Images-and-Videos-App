package ui.dendi.finder.app.core.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.app.core.base.BaseConverter
import ui.dendi.finder.app.feature_videos.data.local.SmallCache

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