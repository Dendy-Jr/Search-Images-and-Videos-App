package ui.dendi.android.finder.core.network

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ui.dendi.android.finder.BuildConfig
import ui.dendi.android.finder.feature_images.data.remote.ImagesApi
import ui.dendi.android.finder.feature_videos.data.remote.VideosApi

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
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
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