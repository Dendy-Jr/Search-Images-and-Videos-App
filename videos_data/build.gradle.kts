apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.videos_domain))

    "implementation"(Dependencies.datastore)
    "implementation"(Dependencies.roomRuntime)
    "kapt"(Dependencies.roomCompiler)
    "implementation"(Dependencies.roomCore)
    "kapt"(Dependencies.roomPersistence)
    "implementation"(Dependencies.roomPaging)

    // Retrofit
    // https://github.com/square/retrofit/releases
    "implementation"(Dependencies.retrofit)
    "implementation"(Dependencies.retrofitConverter)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    "implementation"(Dependencies.timber)
}