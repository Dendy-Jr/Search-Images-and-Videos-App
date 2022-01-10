package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.data.video.cache.*
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class DeleteVideoUseCaseTest {

    private val videoRepository = mock<VideosRepository>()

    private val video = VideoCache(
        2,
        66,
        12,
        473,
        3,
        "https://pixabay.com/videos/id-125/",
        "529927645",
        "flowers, yellow, blossom",
        "film",
        "CoverrFreeFootage",
        1281706,
        "https://cdn.pixabay.com/user/2015/10/16/09-28-45-303_250x250.png",
        VideosStreamsCache(
            LargeCache(
                1080,
                6615235,
                "https://player.vimeo.com/external/135736646.hd.mp4?s=ed02d71c92dd0df7d1110045e6eb65a6&profile_id=119",
                1920
            ),
            MediumCache(
                720,
                3562083,
                "https://player.vimeo.com/external/135736646.hd.mp4?s=ed02d71c92dd0df7d1110045e6eb65a6&profile_id=174",
                1280
            ),
            SmallCache(
                540,
                2030736,
                "https://player.vimeo.com/external/135736646.sd.mp4?s=db2924c48ef91f17fc05da74603d5f89&profile_id=165",
                950
            ),
            TinyCache(
                360,
                1030736,
                "https://player.vimeo.com/external/135736646.sd.mp4?s=db2924c48ef91f17fc05da74603d5f89&profile_id=164",
                640
            )
        ),
        169
    )

    @Test
    fun delete_favorite_video_test() = runBlocking {

        val useCase = DeleteVideoUseCase(repository = videoRepository)

        useCase.deleteFavorite(video)

        Mockito.verify(videoRepository).deleteVideo(video)
    }
}