package com.dendi.android.search_images_and_videos_app.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.data.video.cache.LargeCache
import com.dendi.android.search_images_and_videos_app.data.video.cache.MediumCache
import com.dendi.android.search_images_and_videos_app.data.video.cache.SmallCache
import com.dendi.android.search_images_and_videos_app.data.video.cache.TinyCache
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun fromLarge(large: LargeCache): String {
        val type = object : TypeToken<LargeCache>() {}.type
        return Gson().toJson(large, type)
    }

    @TypeConverter
    fun toLarge(json: String): LargeCache {
        val type = object : TypeToken<LargeCache>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromMedium(medium: MediumCache): String {
        val type = object : TypeToken<MediumCache>() {}.type
        return Gson().toJson(medium, type)
    }

    @TypeConverter
    fun toMedium(json: String): MediumCache {
        val type = object : TypeToken<MediumCache>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromSmall(small: SmallCache): String {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().toJson(small, type)
    }

    @TypeConverter
    fun toSmall(json: String): SmallCache {
        val type = object : TypeToken<SmallCache>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromTiny(tiny: TinyCache): String {
        val type = object : TypeToken<TinyCache>() {}.type
        return Gson().toJson(tiny, type)
    }

    @TypeConverter
    fun toTiny(json: String): TinyCache {
        val type = object : TypeToken<TinyCache>() {}.type
        return Gson().fromJson(json, type)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let {
            return formatter.parse(value, OffsetDateTime::from)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }
}