package com.dendi.android.search_images_and_videos_app.data.core

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeys
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeysDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Database(
    entities = [
        ImageCache::class,
        VideoCache::class,
        ImageRemoteKeys::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PixabayDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun videoDao(): VideoDao

    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao


    companion object {

        @Volatile
        private var INSTANCE: PixabayDb? = null

        fun getInstance(context: Context): PixabayDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PixabayDb::class.java,
                        "myDatabase.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}