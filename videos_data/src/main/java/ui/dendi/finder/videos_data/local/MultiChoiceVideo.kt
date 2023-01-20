package ui.dendi.finder.videos_data.local

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ui.dendi.finder.core.core.models.*
import ui.dendi.finder.videos_data.local.MultiChoiceVideo.Companion.MULTI_CHOICE_VIDEO

@Entity(tableName = MULTI_CHOICE_VIDEO)
data class MultiChoiceVideo(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val comments: Int,
    val date: String,
    val downloads: Int,
    val duration: Int,
    val likes: Int,
    val pageURL: String,
    val pictureId: String,
    val tags: String,
    val type: String,
    val user: String,
    val userId: Int,
    val userImageURL: String,
    @Embedded val videosStreams: MultiChoiceVideosStreams,
    val views: Int,
) {

    fun toDomain() = Video(
        comments = comments,
        date = date,
        downloads = downloads,
        duration = duration,
        id = id,
        likes = likes,
        pageURL = pageURL,
        pictureId = pictureId,
        tags = tags,
        type = type,
        user = user,
        userId = userId,
        userImageURL = userImageURL,
        videos = videosStreams.toDomain(),
        views = views,
    )

    companion object {
        const val MULTI_CHOICE_VIDEO = "MULTI_CHOICE_VIDEO"
    }
}

@Parcelize
data class MultiChoiceVideosStreams(
    val large: @RawValue MultiChoiceLarge,
    val medium: @RawValue MultiChoiceMedium,
    val small: @RawValue MultiChoiceSmall,
    val tiny: @RawValue MultiChoiceTiny,
) : Parcelable {

    fun toDomain() = VideosStreams(
        large = large.toDomain(),
        medium = medium.toDomain(),
        small = small.toDomain(),
        tiny = tiny.toDomain(),
    )
}

data class MultiChoiceLarge(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Large(height = height, size = size, url = url, width = width)
}

data class MultiChoiceMedium(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Medium(height = height, size = size, url = url, width = width)
}

data class MultiChoiceSmall(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Small(height = height, size = size, url = url, width = width)
}

data class MultiChoiceTiny(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Tiny(height = height, size = size, url = url, width = width)
}