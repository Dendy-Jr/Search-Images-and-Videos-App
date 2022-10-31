package ui.dendi.finder.favorites_data.videos.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.favorites_data.videos.MediumCache

class MediumConverter: BaseConverter<MediumCache, String> {

    @TypeConverter
    override fun toRaw(value: MediumCache?): String? {
        val type = object : TypeToken<MediumCache>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): MediumCache? {
        val type = object : TypeToken<MediumCache>() {}.type
        return Gson().fromJson(raw, type)
    }
}