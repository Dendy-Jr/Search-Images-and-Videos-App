package ui.dendi.finder.videos_data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.videos_data.local.MultiChoiceLarge

class MultiChoiceLargeConverter : BaseConverter<MultiChoiceLarge, String> {

    @TypeConverter
    override fun toRaw(value: MultiChoiceLarge?): String? {
        val type = object : TypeToken<MultiChoiceLarge>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): MultiChoiceLarge? {
        val type = object : TypeToken<MultiChoiceLarge>() {}.type
        return Gson().fromJson(raw, type)
    }
}