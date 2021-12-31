package com.dendi.android.search_images_and_videos_app.data.image.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeys.Companion.IMAGE_REMOTE_KEY_TABLE

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Entity(tableName = IMAGE_REMOTE_KEY_TABLE)
data class ImageRemoteKeys(
    @PrimaryKey
    val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
) {
    companion object {
        const val IMAGE_REMOTE_KEY_TABLE = "image_remote_key_table"
    }
}