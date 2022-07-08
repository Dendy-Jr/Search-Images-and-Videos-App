package com.dendi.android.search_images_and_videos_app.core.db.converters

import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.core.base.BaseConverter
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.MediumCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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