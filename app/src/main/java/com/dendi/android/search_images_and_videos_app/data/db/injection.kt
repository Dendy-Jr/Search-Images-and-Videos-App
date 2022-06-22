package com.dendi.android.search_images_and_videos_app.data.db

import android.app.Application
import androidx.room.Room
import com.dendi.android.search_images_and_videos_app.data.image.cache.FavoriteImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeysDao
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun database(application: Application): PixabayDb =
        Room.databaseBuilder(
            application,
            PixabayDb::class.java,
            "myDatabase.db",
        )
            .fallbackToDestructiveMigration()
            .build()
}

@Module
@InstallIn(SingletonComponent::class)
class DatabaseDaoModule {

    @Provides
    fun imageDao(db: PixabayDb): ImageDao =
        db.imageDao()

    @Provides
    fun videoDao(db: PixabayDb): VideoDao =
        db.videoDao()

    @Provides
    fun imageRemoteKeysDao(db: PixabayDb): ImageRemoteKeysDao =
        db.imageRemoteKeysDao()

    @Provides
    fun favoriteImageDao(db: PixabayDb): FavoriteImageDao =
        db.favoriteImageDao()
}