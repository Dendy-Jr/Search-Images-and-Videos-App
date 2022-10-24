object BuildPlugins {
    val android by lazy { "com.android.tools.build:gradle:${Versions.androidGradle}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradle}" }
    val navigation by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigationSaveArgs}" }
    val kotlinSerialization by lazy { "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinSerialization}" }
    val hiltGradle by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltGradle}" }
    val googleServices by lazy { "com.google.gms:google-services:${Versions.googleServices}" }
}

object Dependencies {
    val coroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}" }
    val serialization by lazy { "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}" }

    // Android Jetpack
    // https://developer.android.com/jetpack/androidx/releases/
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.androidxCore}" }
    val activity by lazy { "androidx.activity:activity-ktx:${Versions.androidxActivity}" }
    val fragment by lazy { "androidx.fragment:fragment-ktx:${Versions.androidxFragment}" }
    val appcompat by lazy { "androidx.appcompat:appcompat:${Versions.androidxAppcompat}" }
    val raintlayout by lazy { "androidx.raintlayout:raintlayout:${Versions.androidxConstraintLayout}" }
    val recyclerview by lazy { "androidx.recyclerview:recyclerview:${Versions.androidxRecyclerView}" }
    val viewpager2 by lazy { "androidx.viewpager2:viewpager2:${Versions.androidxViewPager2}" }
    val paging by lazy { "androidx.paging:paging-runtime-ktx:${Versions.androidxPaging}" }
    val swiperefreshlayout by lazy { "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.androidxSwiperefresh}" }
    val navigationCommon by lazy { "androidx.navigation:navigation-common-ktx:${Versions.androidxNavigation}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}" }
    val navigationUi by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigation}" }
    val lifecycleCommon by lazy { "androidx.lifecycle:lifecycle-common-java8:${Versions.androidxLifecycle}" }
    val lifecycleRuntime by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidxLifecycle}" }
    val lifecycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycle}" }
    val lifecycleLivedata by lazy { "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidxLifecycle}" }
    val lifecycleSavedState by lazy { "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.androidxLifecycle}" }
    val lifecycleProcess by lazy { "androidx.lifecycle:lifecycle-process:${Versions.androidxLifecycle}" }
    val workmanagerRuntime by lazy { "androidx.work:work-runtime-ktx:${Versions.androidxWorkmanagerRuntime}" }
    val workmanagerHilt by lazy { "androidx.hilt:hilt-work:${Versions.androidxWorkmanagerHiltWork}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraintLayout}" }
    val splashScreen by lazy { "androidx.core:core-splashscreen:${Versions.splashScreen}" }

    //platform
    val firebaseBom by lazy { "com.google.firebase:firebase-bom:${Versions.firebaseBom}" }
    val firebaseAnalytics by lazy { "com.google.firebase:firebase-analytics-ktx" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.androidxRoom}" }

    // kapt
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.androidxRoom}" }
    val roomCore by lazy { "androidx.room:room-ktx:${Versions.androidxRoom}" }

    //kapt
    val roomPersistence by lazy { "android.arch.persistence.room:compiler:1.1.1" }
    val roomPaging by lazy { "androidx.room:room-paging:${Versions.androidxRoom}" }

    // Dagger
    // https://github.com/google/dagger/releases
    val daggerHilt by lazy { "com.google.dagger:hilt-android:${Versions.daggerHilt}" }

    // kapt
    val daggerHiltCompiler by lazy { "com.google.dagger:hilt-compiler:${Versions.daggerHilt}" }

    // Gson
    // https://github.com/google/gson
    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }

    // Retrofit
    // https://github.com/square/retrofit/releases
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }

    // OkHttp
    // https://github.com/square/okhttp/releases
    val okhttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttp}" }
    val okhttpLoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}" }
    val okhttpTls by lazy { "com.squareup.okhttp3:okhttp-tls:${Versions.okhttp}" }

    // Timber
    // https://github.com/JakeWharton/timber/blob/master/CHANGELOG.md
    val timber by lazy { "com.jakewharton.timber:timber:${Versions.timber}" }

    // Glide
    // https://github.com/bumptech/glide
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }

    // annotationProcessor
    val glideCompiler by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }

    // ExoPlayer
    // https://github.com/google/ExoPlayer
    val exoplayer by lazy { "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}" }

    // Kohii
    // https://github.com/eneim/kohii
    val kohiiCore by lazy { "im.ene.kohii:kohii-core:${Versions.kohii}" }
    val kohiiExoplayer by lazy { "im.ene.kohii:kohii-exoplayer:${Versions.kohii}" }

    // Lottie
    // https://github.com/airbnb/lottie-android
    // Apache License 2.0, https://github.com/airbnb/lottie-android/blob/master/LICENSE
    val lottie by lazy { "com.airbnb.android:lottie:${Versions.lottie}" }

    // ViewBindingPropertyDelegate
    // https://github.com/androidbroadcast/ViewBindingPropertyDelegate
    val viewBinding by lazy { "com.github.kirich1409:viewbindingpropertydelegate:${Versions.viewBinding}" }

    val material by lazy { "com.google.android.material:material:${Versions.androidxMaterial}" }

    // SwipeRevealLayout
    // https://github.com/chthai64/SwipeRevealLayout
    val swipeRevealLayout by lazy { "com.chauthai.swipereveallayout:swipe-reveal-layout:${Versions.swipeRevealLayout}" }
    val datastore by lazy { "androidx.datastore:datastore-preferences:${Versions.androidxDataStore}" }
}