// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.androidGradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradle}")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSaveArgs}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinSerialization}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}")
        classpath("com.google.gms:google-services:${Versions.googleServices}")
    }
}

task<Delete>("clear") {
    delete = setOf(rootProject.buildDir)
}