package ui.dendi.finder.app.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ui.dendi.finder.app.core.db.converters.*
import ui.dendi.finder.app.feature_favorites.data.FavoriteImage
import ui.dendi.finder.app.feature_favorites.data.FavoriteImageDao
import ui.dendi.finder.app.feature_images.data.local.ImageCache
import ui.dendi.finder.app.feature_images.data.local.ImageDao
import ui.dendi.finder.app.feature_images.data.local.ImageRemoteKeys
import ui.dendi.finder.app.feature_images.data.local.ImageRemoteKeysDao
import ui.dendi.finder.app.feature_videos.data.local.VideoCache
import ui.dendi.finder.app.feature_videos.data.local.VideoDao


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