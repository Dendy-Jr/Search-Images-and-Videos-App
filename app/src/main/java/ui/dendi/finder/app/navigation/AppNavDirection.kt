package ui.dendi.finder.app.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.finder.app.NavGraphDirections
import ui.dendi.finder.app.feature_images.domain.Image
import ui.dendi.finder.app.feature_images.presentation.detail.ImageDetailFragmentArgs
import ui.dendi.finder.app.feature_videos.domain.Video
import ui.dendi.finder.app.feature_videos.presentation.detail.VideoDetailsFragmentArgs
import javax.inject.Inject
import javax.inject.Singleton

interface AppNavDirections {

    val mainScreen: NavDirections

    fun imageDetailsScreen(image: Image): NavDirections

    fun videoDetailsScreen(video: Video): NavDirections
}

fun NavDirections.extendWith(args: Bundle): NavDirections = object : NavDirections {
    override val actionId: Int = this@extendWith.actionId

    override val arguments: Bundle = args
}

@Singleton
class AppNavDirectionsImpl @Inject constructor() : AppNavDirections {

    override val mainScreen: NavDirections
        get() = NavGraphDirections.navigateToMainScreen()

    override fun imageDetailsScreen(image: Image): NavDirections =
        NavGraphDirections.navigateToImageDetailsScreen()
            .extendWith(ImageDetailFragmentArgs(image).toBundle())

    override fun videoDetailsScreen(video: Video): NavDirections =
        NavGraphDirections.navigateToVideoDetailsScreen()
            .extendWith(VideoDetailsFragmentArgs(video).toBundle())
}

@InstallIn(SingletonComponent::class)
@Module
interface AppNavDirectionsModule {

    @Binds
    fun bindNavDirections(impl: AppNavDirectionsImpl): AppNavDirections
}