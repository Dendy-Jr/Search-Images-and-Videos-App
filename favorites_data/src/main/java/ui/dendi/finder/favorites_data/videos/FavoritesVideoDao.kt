package ui.dendi.finder.favorites_data.videos

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesVideoDao {

    @Query("SELECT * FROM ${FavoriteVideo.TABLE_VIDEOS} ORDER BY date DESC")
    fun getVideos(): Flow<List<FavoriteVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: FavoriteVideo)

    @Delete
    suspend fun deleteVideo(image: FavoriteVideo)

    @Query("DELETE FROM ${FavoriteVideo.TABLE_VIDEOS}")
    suspend fun deleteAllVideos()
}