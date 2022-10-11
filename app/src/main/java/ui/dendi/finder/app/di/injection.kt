package ui.dendi.finder.app.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.finder.favorites_data.images.FavoritesImageDao
import ui.dendi.finder.images_data.local.ImageRemoteKeysDao
import ui.dendi.finder.videos_data.local.VideoDao
import ui.dendi.finder.app.PixabayDb
import ui.dendi.finder.images_data.local.ImageDao
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
    fun favoriteImageDao(db: PixabayDb): FavoritesImageDao =
        db.favoriteImageDao()
}