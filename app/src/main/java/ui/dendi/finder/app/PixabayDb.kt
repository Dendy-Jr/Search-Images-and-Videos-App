package ui.dendi.finder.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ui.dendi.finder.favorites_data.images.FavoriteImage
import ui.dendi.finder.favorites_data.images.FavoritesImageDao
import ui.dendi.finder.images_data.local.ImageEntity
import ui.dendi.finder.images_data.local.ImageRemoteKeys
import ui.dendi.finder.images_data.local.ImageRemoteKeysDao
import ui.dendi.finder.core.core.db.converters.*
import ui.dendi.finder.images_data.local.ImageDao
import ui.dendi.finder.videos_data.local.VideoCache
import ui.dendi.finder.videos_data.local.VideoDao
import ui.dendi.finder.videos_data.local.converters.LargeConverter
import ui.dendi.finder.videos_data.local.converters.MediumConverter
import ui.dendi.finder.videos_data.local.converters.SmallConverter
import ui.dendi.finder.videos_data.local.converters.TinyConverter

@Database(
    entities = [
        ImageEntity::class,
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

    abstract fun favoriteImageDao(): FavoritesImageDao
}