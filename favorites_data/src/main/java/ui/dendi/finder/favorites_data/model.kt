package ui.dendi.finder.favorites_data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ui.dendi.finder.favorites_data.FavoriteImage.Companion.FAVORITE_IMAGE_TABLE
import ui.dendi.finder.images_data.local.ImageEntity

@Entity(tableName = FAVORITE_IMAGE_TABLE)
data class FavoriteImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded(prefix = "Image_")
    val image: ImageEntity,
) {
    companion object {
        const val FAVORITE_IMAGE_TABLE = "favorite_image_table"
    }
}