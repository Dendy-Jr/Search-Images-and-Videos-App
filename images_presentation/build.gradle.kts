apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.images_data))
    "implementation"(project(Modules.images_domain))

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

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    "implementation"(Dependencies.viewBinding)

    "implementation"(Dependencies.material)

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    "implementation"(Dependencies.swipeRevealLayout)
}