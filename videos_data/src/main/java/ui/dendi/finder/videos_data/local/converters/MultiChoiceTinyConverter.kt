package ui.dendi.finder.videos_data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.videos_data.local.MultiChoiceTiny

class MultiChoiceTinyConverter : BaseConverter<MultiChoiceTiny, String> {

    @TypeConverter
    override fun toRaw(value: MultiChoiceTiny?): String? {
        val type = object : TypeToken<MultiChoiceTiny>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): MultiChoiceTiny? {
        val type = object : TypeToken<MultiChoiceTiny>() {}.type
        return Gson().fromJson(raw, type)
    }
}