apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.favorites_data))
    "implementation"(project(Modules.favorites_domain))
    "implementation"(project(Modules.videos_domain))

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    "implementation"(Dependencies.coreKtx)
    "implementation"(Dependencies.activity)
    "implementation"(Dependencies.fragment)
    "implementation"(Dependencies.appcompat)
    "implementation"(Dependencies.constraintLayout)
    "implementation"(Dependencies.recyclerview)
    "implementation"(Dependencies.viewpager2)
    "implementation"(Dependencies.lifecycleViewModel)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    "implementation"(Dependencies.timber)

    // Glide
    // https://github.com/bumptech/glide
    "implementation"(Dependencies.glide)
    "annotationProcessor"(Dependencies.glideCompiler)

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    "implementation"(Dependencies.exoplayer)

    // Kohii
    // https://github.com/eneim/kohii
    "implementation"(Dependencies.kohiiCore)
    "implementation"(Dependencies.kohiiExoplayer)

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    "implementation"(Dependencies.viewBinding)

    "implementation"(Dependencies.material)

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    "implementation"(Dependencies.swipeRevealLayout)
}