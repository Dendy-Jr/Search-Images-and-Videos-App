package ui.dendi.finder.core.core.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.models.Video

interface AppNavDirections {

    val mainScreen: NavDirections

    fun imageDetailsScreen(image: Image): NavDirections

    fun videoDetailsScreen(video: Video): NavDirections
}

fun NavDirections.extendWith(args: Bundle): NavDirections = object : NavDirections {
    override val actionId: Int = this@extendWith.actionId

    override val arguments: Bundle = args
}