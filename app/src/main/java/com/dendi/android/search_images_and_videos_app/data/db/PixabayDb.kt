package com.dendi.android.search_images_and_videos_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dendi.android.search_images_and_videos_app.data.image.cache.*
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache

@Database(
    entities = [
        ImageCache::class,
        VideoCache::class,
        ImageRemoteKeys::class,
        FavoriteImage::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PixabayDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun videoDao(): VideoDao

    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao

    abstract fun favoriteImageDao(): FavoriteImageDao
}