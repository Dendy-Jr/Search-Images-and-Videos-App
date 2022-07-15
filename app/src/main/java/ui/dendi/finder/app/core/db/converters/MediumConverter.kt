package ui.dendi.finder.app.core.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.app.core.base.BaseConverter
import ui.dendi.finder.app.feature_videos.data.local.MediumCache

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