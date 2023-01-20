package ui.dendi.finder.videos_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.videos_data.local.MultiChoiceVideo.Companion.MULTI_CHOICE_VIDEO

@Dao
interface MultiChoiceVideosDao {

    @Query("SELECT * FROM $MULTI_CHOICE_VIDEO")
    fun getMultiChoiceVideos(): Flow<List<MultiChoiceVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiChoiceVideos(videos: List<MultiChoiceVideo>)

    @Query("DELETE FROM $MULTI_CHOICE_VIDEO")
    suspend fun clearAll()
}