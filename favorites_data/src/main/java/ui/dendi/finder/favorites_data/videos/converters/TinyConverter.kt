package ui.dendi.finder.favorites_data.videos.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.favorites_data.videos.TinyCache

class TinyConverter : BaseConverter<TinyCache, String> {

    @TypeConverter
    override fun toRaw(value: TinyCache?): String? {
        val type = object : TypeToken<TinyCache>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): TinyCache? {
        val type = object : TypeToken<TinyCache>() {}.type
        return Gson().fromJson(raw, type)
    }
}