plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 21
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(Modules.images_domain))

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
    implementation(Dependencies.viewpager2)
    implementation(Dependencies.paging)
    implementation(Dependencies.swiperefreshlayout)
    implementation(Dependencies.navigationCommon)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.lifecycleCommon)
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.lifecycleViewModel)
    implementation(Dependencies.lifecycleLivedata)
    implementation(Dependencies.lifecycleSavedState)
    implementation(Dependencies.lifecycleProcess)
    implementation(Dependencies.workmanagerRuntime)
    implementation(Dependencies.workmanagerHilt)
    implementation(Dependencies.datastore)
    implementation(platform(Dependencies.firebaseBom))
    implementation(Dependencies.firebaseAnalytics)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomCore)
    kapt(Dependencies.roomPersistence)
    implementation(Dependencies.roomPaging)

    // Dagger
    // https://github.com/google/dagger/releases
    implementation(Dependencies.daggerHilt)
    kapt(Dependencies.daggerHiltCompiler)

    // Gson
    // https://github.com/google/gson
    implementation(Dependencies.gson)

    // Retrofit
    // https://github.com/square/retrofit/releases
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofitConverter)

    // OkHttp
    // https://github.com/square/okhttp/releases
    implementation(Dependencies.okhttp)
    implementation(Dependencies.okhttpLoggingInterceptor)
    implementation(Dependencies.okhttpTls)

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    implementation(Dependencies.timber)

    // Glide
    // https://github.com/bumptech/glide
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glideCompiler)

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    implementation(Dependencies.exoplayer)

    // Kohii
    // https://github.com/eneim/kohii
    implementation(Dependencies.kohiiCore)
    implementation(Dependencies.kohiiExoplayer)

    // Lottie
    // https://github.com/airbnb/lottie-android
    // Apache License 2.0, https://github.com/airbnb/lottie-android/blob/master/LICENSE
    implementation(Dependencies.lottie)

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    implementation(Dependencies.viewBinding)

    implementation(Dependencies.material)

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    implementation(Dependencies.swipeRevealLayout)

//    implementation("androidx.core:core-ktx:1.7.0")
//    implementation("androidx.appcompat:appcompat:1.5.0")
//    implementation("com.google.android.material:material:1.6.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}