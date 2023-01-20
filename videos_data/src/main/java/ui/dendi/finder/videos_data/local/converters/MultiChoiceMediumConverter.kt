package ui.dendi.finder.videos_data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.videos_data.local.MultiChoiceMedium

class MultiChoiceMediumConverter : BaseConverter<MultiChoiceMedium, String> {

    @TypeConverter
    override fun toRaw(value: MultiChoiceMedium?): String? {
        val type = object : TypeToken<MultiChoiceMedium>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): MultiChoiceMedium? {
        val type = object : TypeToken<MultiChoiceMedium>() {}.type
        return Gson().fromJson(raw, type)
    }
}