plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    defaultConfig {
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        vectorDrawables {
            useSupportLibrary = true
        }
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    viewBinding {
        android.buildFeatures.viewBinding = true
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.videos_domain))
    implementation(project(Modules.videos_data))

    implementation(Dependencies.coroutines)

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.activity)
    implementation(Dependencies.fragment)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.recyclerview)
    implementation(Dependencies.paging)
    implementation(Dependencies.swiperefreshlayout)
    implementation(Dependencies.navigationCommon)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.lifecycleViewModel)
    implementation(Dependencies.workmanagerRuntime)
    implementation(Dependencies.workmanagerHilt)

    // Dagger
    // https://github.com/google/dagger/releases
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    implementation(Dependencies.timber)

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    implementation(Dependencies.exoplayer)

    // Kohii
    // https://github.com/eneim/kohii
    implementation(Dependencies.kohiiCore)
    implementation(Dependencies.kohiiExoplayer)

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    implementation(Dependencies.viewBinding)

    implementation(Dependencies.material)

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    implementation(Dependencies.swipeRevealLayout)
}