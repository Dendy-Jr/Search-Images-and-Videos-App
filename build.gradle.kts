// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSaveArgs}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinSerialization}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}")
        classpath("com.google.gms:google-services:${Versions.googleServices}")
    }
}

task<Delete>("clear") {
    delete = setOf(rootProject.buildDir)
}