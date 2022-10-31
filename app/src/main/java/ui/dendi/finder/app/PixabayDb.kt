package ui.dendi.finder.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ui.dendi.finder.favorites_data.images.FavoriteImage
import ui.dendi.finder.favorites_data.images.FavoritesImageDao
import ui.dendi.finder.core.core.db.converters.*
import ui.dendi.finder.favorites_data.videos.FavoriteVideo
import ui.dendi.finder.favorites_data.videos.FavoritesVideoDao
import ui.dendi.finder.favorites_data.videos.converters.LargeConverter
import ui.dendi.finder.favorites_data.videos.converters.MediumConverter
import ui.dendi.finder.favorites_data.videos.converters.SmallConverter
import ui.dendi.finder.favorites_data.videos.converters.TinyConverter

@Database(
    entities = [
        FavoriteVideo::class,
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

    abstract fun videoDao(): FavoritesVideoDao

    abstract fun favoriteImageDao(): FavoritesImageDao
}