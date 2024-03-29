package ui.dendi.finder.videos_data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ui.dendi.finder.core.core.base.BaseConverter
import ui.dendi.finder.videos_data.local.MultiChoiceSmall

class MultiChoiceSmallConverter : BaseConverter<MultiChoiceSmall, String> {

    @TypeConverter
    override fun toRaw(value: MultiChoiceSmall?): String? {
        val type = object : TypeToken<MultiChoiceSmall>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): MultiChoiceSmall? {
        val type = object : TypeToken<MultiChoiceSmall>() {}.type
        return Gson().fromJson(raw, type)
    }
}