package ui.dendi.finder.favorites_data.images

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesImageDao {

    @Query("SELECT * FROM ${FavoriteImage.FAVORITE_IMAGE_TABLE} ORDER BY Image_date DESC")
    fun getFavoritesImage(): Flow<List<FavoriteImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: FavoriteImage)

    @Delete
    suspend fun deleteImage(image: FavoriteImage)

    @Query("DELETE FROM ${FavoriteImage.FAVORITE_IMAGE_TABLE}")
    suspend fun clearAll()
}