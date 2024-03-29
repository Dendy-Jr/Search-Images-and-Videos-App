apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.settings_domain))

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    "implementation"(Dependencies.coreKtx)
    "implementation"(Dependencies.activity)
    "implementation"(Dependencies.fragment)
    "implementation"(Dependencies.appcompat)
    "implementation"(Dependencies.constraintLayout)
    "implementation"(Dependencies.recyclerview)
    "implementation"(Dependencies.navigationCommon)
    "implementation"(Dependencies.navigationFragment)
    "implementation"(Dependencies.navigationUi)
    "implementation"(Dependencies.lifecycleViewModel)
    "implementation"(Dependencies.material)

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    "implementation"(Dependencies.viewBinding)
}