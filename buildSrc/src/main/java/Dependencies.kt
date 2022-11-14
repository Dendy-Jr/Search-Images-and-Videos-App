object BuildPlugins {
    const val android = "com.android.tools.build:gradle:${Versions.androidGradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradle}"
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSaveArgs}"
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinSerialization}"
    const val hiltGradle = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}"
    const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
}

object Dependencies {
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}"

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    const val coreKtx = "androidx.core:core-ktx:${Versions.androidxCore}"
    const val activity = "androidx.activity:activity-ktx:${Versions.androidxActivity}"
    const val fragment = "androidx.fragment:fragment-ktx:${Versions.androidxFragment}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.androidxAppcompat}"
    const val raintlayout = "androidx.raintlayout:raintlayout:${Versions.androidxConstraintLayout}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.androidxViewPager2}"
    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.androidxPaging}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidxSwiperefresh}"
    const val navigationCommon = "androidx.navigation:navigation-common-ktx:${Versions.androidxNavigation}"
    const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    const val lifecycleCommon = "androidx.lifecycle:lifecycle-common-java8:${Versions.androidxLifecycle}"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}"
    const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}"
    const val lifecycleSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.androidxLifecycle}"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.androidxLifecycle}"
    const val workmanagerRuntime = "androidx.work:work-runtime-ktx:${Versions.androidxWorkmanagerRuntime}"
    const val workmanagerHilt = "androidx.hilt:hilt-work:${Versions.androidxWorkmanagerHiltWork}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}"
    const val splashScreen = "androidx.core:core-splashscreen:${Versions.splashScreen}"

    //platform
    const val firebaseBom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.androidxRoom}"

    // kapt
    const val roomCompiler = "androidx.room:room-compiler:${Versions.androidxRoom}"
    const val roomCore = "androidx.room:room-ktx:${Versions.androidxRoom}"

    //kapt
    const val roomPersistence = "android.arch.persistence.room:compiler:1.1.1"
    const val roomPaging = "androidx.room:room-paging:${Versions.androidxRoom}"

    // Dagger
    // https://github.com/google/dagger/releases
    const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"

    // kapt
    const val daggerHiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"

    // Gson
    // https://github.com/google/gson
    const val gson = "com.google.code.gson:gson:${Versions.gson}"

    // Retrofit
    // https://github.com/square/retrofit/releases
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    // OkHttp
    // https://github.com/square/okhttp/releases
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttpTls = "com.squareup.okhttp3:okhttp-tls:${Versions.okhttp}"

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"

    // Kohii
    // https://github.com/eneim/kohii
    const val kohiiCore = "im.ene.kohii:kohii-core:${Versions.kohii}"
    const val kohiiExoplayer = "im.ene.kohii:kohii-exoplayer:${Versions.kohii}"

    // Lottie
    // https://github.com/airbnb/lottie-android
    // Apache License 2.0, https://github.com/airbnb/lottie-android/blob/master/LICENSE
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    const val viewBinding = "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding}"

    const val material = "com.google.android.material:material:${Versions.androidxMaterial}"

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    const val swipeRevealLayout = "com.chauthai.swipereveallayout:swipe-reveal-layout:${Versions.swipeRevealLayout}"
    const val datastore = "androidx.datastore:datastore-preferences:${Versions.androidxDataStore}"

    //Coil
    const val coil = ("io.coil-kt:coil:${Versions.coil}")

    // OkHttpProfiler
    // https://github.com/itkacher/OkHttpProfiler
    const val okHttpProfiler = ("com.localebro:okhttpprofiler:${Versions.okHttpProfiler}")
}