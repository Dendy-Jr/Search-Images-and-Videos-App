package com.dendi.android.search_images_and_videos_app.core.db.converters

import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.core.base.BaseConverter
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.SmallCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SmallConverter:BaseConverter<SmallCache, String> {

    @TypeConverter
    override fun toRaw(value: SmallCache?): String? {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    override fun fromRaw(raw: String?): SmallCache? {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().fromJson(raw, type)
    }
}