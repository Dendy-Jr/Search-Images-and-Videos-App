apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.videos_domain))
    "implementation"(project(Modules.videos_data))

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    "implementation"(Dependencies.coreKtx)
    "implementation"(Dependencies.activity)
    "implementation"(Dependencies.fragment)
    "implementation"(Dependencies.appcompat)
    "implementation"(Dependencies.constraintLayout)
    "implementation"(Dependencies.recyclerview)
    "implementation"(Dependencies.paging)
    "implementation"(Dependencies.swiperefreshlayout)
    "implementation"(Dependencies.navigationCommon)
    "implementation"(Dependencies.navigationFragment)
    "implementation"(Dependencies.navigationUi)
    "implementation"(Dependencies.lifecycleViewModel)
    "implementation"(Dependencies.workmanagerRuntime)
    "implementation"(Dependencies.workmanagerHilt)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    "implementation"(Dependencies.timber)

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