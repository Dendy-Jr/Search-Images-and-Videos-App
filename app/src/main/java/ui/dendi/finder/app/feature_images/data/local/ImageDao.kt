package ui.dendi.finder.app.feature_images.data.local

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM ${ImageEntity.TABLE_IMAGES} ORDER BY date")
    fun getImages(): Flow<List<ImageEntity>>

    @Query("SELECT * FROM ${ImageEntity.TABLE_IMAGES} ORDER BY date")
    fun getImagesPagingSource(): PagingSource<Int,ImageEntity>

    @Transaction
    suspend fun refresh(images: List<ImageEntity>) {
        clearAll()
        insertAll(images)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)

    @Delete
    suspend fun deleteImage(image: ImageEntity)

    @Query("DELETE FROM ${ImageEntity.TABLE_IMAGES}")
    suspend fun clearAll()
}