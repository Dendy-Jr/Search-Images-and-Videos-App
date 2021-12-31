package com.dendi.android.search_images_and_videos_app.data.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.dendi.android.search_images_and_videos_app.data.video.cache.LargeEntity
import com.dendi.android.search_images_and_videos_app.data.video.cache.MediumEntity
import com.dendi.android.search_images_and_videos_app.data.video.cache.SmallEntity
import com.dendi.android.search_images_and_videos_app.data.video.cache.TinyEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * @author Dendy-Jr on 21.12.2021
 * olehvynnytskyi@gmail.com
 */
class Converters {

    @TypeConverter
    fun fromLarge(large: LargeEntity): String {
        val type = object : TypeToken<LargeEntity>() {}.type
        return Gson().toJson(large, type)
    }

    @TypeConverter
    fun toLarge(json: String): LargeEntity {
        val type = object : TypeToken<LargeEntity>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromMedium(medium: MediumEntity): String {
        val type = object : TypeToken<MediumEntity>() {}.type
        return Gson().toJson(medium, type)
    }

    @TypeConverter
    fun toMedium(json: String): MediumEntity {
        val type = object : TypeToken<MediumEntity>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromSmall(small: SmallEntity): String {
        val type = object : TypeToken<SmallEntity>() {}.type
        return Gson().toJson(small, type)
    }

    @TypeConverter
    fun toSmall(json: String): SmallEntity {
        val type = object : TypeToken<SmallEntity>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromTiny(tiny: TinyEntity): String {
        val type = object : TypeToken<TinyEntity>() {}.type
        return Gson().toJson(tiny, type)
    }

    @TypeConverter
    fun toTiny(json: String): TinyEntity {
        val type = object : TypeToken<TinyEntity>() {}.type
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