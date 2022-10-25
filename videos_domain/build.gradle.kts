apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.favorites_domain))

    "implementation"(Dependencies.paging)
}