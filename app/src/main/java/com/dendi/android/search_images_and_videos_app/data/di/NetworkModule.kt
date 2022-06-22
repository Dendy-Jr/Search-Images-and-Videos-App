package com.dendi.android.search_images_and_videos_app.data.di

import com.dendi.android.search_images_and_videos_app.BuildConfig
import com.dendi.android.search_images_and_videos_app.core.DownloadFileApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesApi
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
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

    @Provides
    fun provideDownloadImagesApi(retrofit: Retrofit): ImagesApi {
        return retrofit.create(ImagesApi::class.java)
    }

    @Provides
    fun provideDownloadVideosApi(retrofit: Retrofit): VideosApi {
        return retrofit.create(VideosApi::class.java)
    }

    @Provides
    fun provideDownloadFileApi(retrofit: Retrofit): DownloadFileApi {
        return retrofit.create(DownloadFileApi::class.java)
    }
}