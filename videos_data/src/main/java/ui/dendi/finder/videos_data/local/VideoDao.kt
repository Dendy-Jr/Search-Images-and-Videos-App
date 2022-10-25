package ui.dendi.finder.videos_data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Query("SELECT * FROM ${FavoriteVideo.TABLE_VIDEOS}")
    fun getVideos(): Flow<List<FavoriteVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: FavoriteVideo)

    @Delete
    suspend fun deleteVideo(image: FavoriteVideo)

    @Query("DELETE FROM ${FavoriteVideo.TABLE_VIDEOS}")
    suspend fun deleteAllVideos()
}