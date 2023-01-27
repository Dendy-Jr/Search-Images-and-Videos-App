plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("plugin.serialization")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    compileSdk = ConfigData.compileSdkVersion
    buildToolsVersion = ConfigData.buildToolsVersion

    sourceSets {
        getByName("main") {
            java {
                srcDirs("build/generated/source/navigation-args")
            }
        }
    }

    defaultConfig {
        applicationId = "ui.dendi.finder.app"
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion
        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "PIXABAY_API_KEY", "\"22377618-bb4b614e69696a850b2455e19\"")
        buildConfigField("String", "BASE_URL", "\"https://pixabay.com/api/\"")

        kapt {
            arguments {
                arg("room.schemaLocation", "$rootDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
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
        freeCompilerArgs = listOf("-Xcontext-receivers")
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
    implementation(project(Modules.favorites_data))
    implementation(project(Modules.favorites_domain))
    implementation(project(Modules.favorites_presentation))
    implementation(project(Modules.images_data))
    implementation(project(Modules.images_domain))
    implementation(project(Modules.images_presentation))
    implementation(project(Modules.videos_data))
    implementation(project(Modules.videos_domain))
    implementation(project(Modules.videos_presentation))
    implementation(project(Modules.settings_data))
    implementation(project(Modules.settings_domain))
    implementation(project(Modules.settings_presentation))

    implementation(Dependencies.coroutines)
    implementation(Dependencies.serialization)

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.activity)
    implementation(Dependencies.fragment)
    implementation(Dependencies.appcompat)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.recyclerview)
    implementation(Dependencies.navigationCommon)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.lifecycleViewModel)
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomCore)
    kapt(Dependencies.roomPersistence)
    implementation(Dependencies.roomPaging)
    implementation(Dependencies.splashScreen)

    // Dagger
    // https://github.com/google/dagger/releases
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)

    // Retrofit
    // https://github.com/square/retrofit/releases
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverter)

    // OkHttp
    // https://github.com/square/okhttp/releases
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLoggingInterceptor)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    implementation(Dependencies.timber)
    implementation(Dependencies.viewBinding)

    // OkHttpProfiler
    // https://github.com/itkacher/OkHttpProfiler
    implementation(Dependencies.okHttpProfiler)
}

kapt {
    correctErrorTypes = true
}