package ui.dendi.finder.app.core.db

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.finder.app.feature_favorites.data.FavoriteImageDao
import ui.dendi.finder.app.feature_images.data.local.ImageDao
import ui.dendi.finder.app.feature_images.data.local.ImageRemoteKeysDao
import ui.dendi.finder.app.feature_videos.data.local.VideoDao
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