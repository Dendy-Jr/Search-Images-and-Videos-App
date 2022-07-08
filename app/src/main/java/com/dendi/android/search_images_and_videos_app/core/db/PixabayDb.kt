package com.dendi.android.search_images_and_videos_app.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dendi.android.search_images_and_videos_app.core.db.converters.*
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.VideoDao
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.VideoCache
import com.dendi.android.search_images_and_videos_app.feature_favorites.data.FavoriteImage
import com.dendi.android.search_images_and_videos_app.feature_favorites.data.FavoriteImageDao
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImageCache
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImageDao
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImageRemoteKeys
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImageRemoteKeysDao

@Database(
    entities = [
        ImageCache::class,
        VideoCache::class,
        ImageRemoteKeys::class,
        FavoriteImage::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(
    LargeConverter::class,
    MediumConverter::class,
    SmallConverter::class,
    TinyConverter::class,
    DateConverter::class,
)
abstract class PixabayDb : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun videoDao(): VideoDao

    abstract fun imageRemoteKeysDao(): ImageRemoteKeysDao

    abstract fun favoriteImageDao(): FavoriteImageDao
}