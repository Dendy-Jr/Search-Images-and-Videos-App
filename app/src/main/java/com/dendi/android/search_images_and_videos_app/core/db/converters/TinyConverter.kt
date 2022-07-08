package com.dendi.android.search_images_and_videos_app.core.db.converters

import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.core.base.BaseConverter
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.TinyCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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