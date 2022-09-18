package ui.dendi.finder.app.navigation

import androidx.navigation.NavDirections
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ui.dendi.finder.app.NavGraphDirections
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.core.core.navigation.AppNavDirections
import ui.dendi.finder.core.core.navigation.extendWith
import ui.dendi.finder.images_presentation.detail.ImageDetailFragmentArgs
import ui.dendi.finder.videos_presentation.detail.VideoDetailsFragmentArgs
import javax.inject.Inject
import javax.inject.Singleton

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