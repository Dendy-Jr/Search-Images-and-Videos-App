package com.dendi.android.search_images_and_videos_app.di

import com.dendi.android.search_images_and_videos_app.BuildConfig
import com.dendi.android.search_images_and_videos_app.core.InternetConnection
import com.dendi.android.search_images_and_videos_app.data.core.PixabayApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.FileApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesApi
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */

@ExperimentalSerializationApi
val networkPhotoModule = module {

    fun getRetrofit(): Retrofit {

        val okHttpBuilder = OkHttpClient().newBuilder()
        okHttpBuilder.addInterceptor {
            val request = it.request()
            val url = request.url.newBuilder()
                .addQueryParameter("key", BuildConfig.PIXABAY_API_KEY)
                .build()
            it.proceed(request.newBuilder().url(url).build())
        }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    single<Retrofit> { getRetrofit() }

    fun getPixabayApi(retrofit: Retrofit): PixabayApi {
        return retrofit.create(PixabayApi::class.java)
    }

    fun getImagesApi(retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }

    fun getVideosApi(retrofit: Retrofit): VideosApi {
        return retrofit.create(VideosApi::class.java)
    }

    fun getFileApi(retrofit: Retrofit): FileApi {
        return retrofit.create(FileApi::class.java)
    }

    factory { getImagesApi(retrofit = get()) }

    factory { getVideosApi(retrofit = get()) }

    factory { getPixabayApi(retrofit = get()) }

    factory { getFileApi(retrofit = get()) }

    factory<InternetConnection> { InternetConnection.CheckInternetConnectionImpl(context = androidApplication()) }
}