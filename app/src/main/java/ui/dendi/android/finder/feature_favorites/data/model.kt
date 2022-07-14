package ui.dendi.android.finder.feature_favorites.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ui.dendi.android.finder.feature_favorites.data.FavoriteImage.Companion.FAVORITE_IMAGE_TABLE
import ui.dendi.android.finder.feature_images.data.local.ImageCache

@Entity(tableName = FAVORITE_IMAGE_TABLE)
data class FavoriteImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded(prefix = "Image_")
    val image: ImageCache,
) {
    companion object {
        const val FAVORITE_IMAGE_TABLE = "favorite_image_table"
    }
}