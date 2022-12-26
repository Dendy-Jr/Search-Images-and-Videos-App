package ui.dendi.finder.app

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ui.dendi.finder.core.core.db.converters.DateConverter
import ui.dendi.finder.favorites_data.images.FavoriteImage
import ui.dendi.finder.favorites_data.images.FavoritesImageDao
import ui.dendi.finder.favorites_data.videos.FavoriteVideo
import ui.dendi.finder.favorites_data.videos.FavoritesVideoDao
import ui.dendi.finder.favorites_data.videos.converters.LargeConverter
import ui.dendi.finder.favorites_data.videos.converters.MediumConverter
import ui.dendi.finder.favorites_data.videos.converters.SmallConverter
import ui.dendi.finder.favorites_data.videos.converters.TinyConverter
import ui.dendi.finder.images_data.local.MultiChoiceImage
import ui.dendi.finder.images_data.local.MultiChoiceImagesDao

@Database(
    entities = [
        MultiChoiceImage::class,
        FavoriteImage::class,
        FavoriteVideo::class,
    ], version = 1, exportSchema = false
)

@TypeConverters(
    DateConverter::class,
    LargeConverter::class,
    MediumConverter::class,
    SmallConverter::class,
    TinyConverter::class,
)
abstract class PixabayDb : RoomDatabase() {

    abstract fun multiChoiceImagesDao(): MultiChoiceImagesDao

    abstract fun favoriteImageDao(): FavoritesImageDao

    abstract fun videoDao(): FavoritesVideoDao
}