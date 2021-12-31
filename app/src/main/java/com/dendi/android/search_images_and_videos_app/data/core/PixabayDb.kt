package com.dendi.android.search_images_and_videos_app.data.core

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeys
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeysDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Database(
    entities = [
        ImageEntity::class,
        VideoEntity::class,
        ImageRemoteKeys::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PixabayDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun videoDao(): VideoDao

    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao
}