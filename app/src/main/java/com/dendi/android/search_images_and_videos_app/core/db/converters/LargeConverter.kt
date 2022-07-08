package com.dendi.android.search_images_and_videos_app.core.db.converters

import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.core.base.BaseConverter
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.LargeCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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