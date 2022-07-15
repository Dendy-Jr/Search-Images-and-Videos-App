package ui.dendi.finder.app.feature_images.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<ImageRemoteKeys>)

    @Query("SELECT * FROM ${ImageRemoteKeys.IMAGE_REMOTE_KEY_TABLE} WHERE repoId = :repoId")
    suspend fun remoteKeysById(repoId: Long): ImageRemoteKeys?

    @Query("DELETE FROM ${ImageRemoteKeys.IMAGE_REMOTE_KEY_TABLE}")
    suspend fun clearRemoteKeys()
}