package ui.dendi.android.finder.core.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.android.finder.core.base.BaseConverter
import ui.dendi.android.finder.feature_videos.data.local.LargeCache

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