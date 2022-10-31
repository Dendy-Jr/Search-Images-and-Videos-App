apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.images_data))
    "implementation"(project(Modules.videos_data))
    "implementation"(project(Modules.favorites_domain))

    // Gson
    // https://github.com/google/gson
    "implementation"(Dependencies.gson)

    "implementation"(Dependencies.roomRuntime)
    "kapt"(Dependencies.roomCompiler)
    "implementation"(Dependencies.roomCore)
    "kapt"(Dependencies.roomPersistence)
    "implementation"(Dependencies.roomPaging)
}