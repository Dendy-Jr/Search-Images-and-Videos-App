package ui.dendi.finder.images_data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.images_data.local.MultiChoiceImage.Companion.MULTI_CHOICE_IMAGE

@Dao
interface MultiChoiceImagesDao {

    @Query("SELECT * FROM $MULTI_CHOICE_IMAGE")
    fun getMultiChoiceImages(): Flow<List<MultiChoiceImage>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMultiChoiceImages(images: List<MultiChoiceImage>)

    @Query("DELETE FROM $MULTI_CHOICE_IMAGE")
    suspend fun clearAll()
}