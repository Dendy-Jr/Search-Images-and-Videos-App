package ui.dendi.finder.app.core.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.app.core.base.BaseConverter
import ui.dendi.finder.app.feature_videos.data.local.LargeCache

class LargeConverter: BaseConverter<LargeCache, String> {

    @TypeConverter
    override fun toRaw(value: LargeCache?): String? {
        val type = object : TypeToken<LargeCache>() {}.type
        return Gson().toJson(value, type)
    }


    @TypeConverter
    override fun fromRaw(raw: String?): LargeCache? {
        val type = object : TypeToken<LargeCache>() {}.type
        return Gson().fromJson(raw, type)
    }
}