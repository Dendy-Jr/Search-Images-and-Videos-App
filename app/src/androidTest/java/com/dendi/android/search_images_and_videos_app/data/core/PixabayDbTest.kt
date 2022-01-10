package com.dendi.android.search_images_and_videos_app.data.core

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageDao
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */
@RunWith(AndroidJUnit4::class)
class PixabayDbTest : TestCase() {

    private lateinit var db: PixabayDb
    private lateinit var imageDao: ImageDao

    private val image = ImageCache(
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        "",
        0,
        "",
        0,
        "",
        0,
        "",
        "",
        "",
        
        "",
        0,
        0,
        "",
        0,
        false
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            PixabayDb::class.java
        ).build()
        imageDao = db.imageDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeAndReadImage() = runBlocking {
        imageDao.insertImage(image)
        imageDao.getImages().take(1).collect {
            assertThat(it.size).isEqualTo(1)
        }
    }

    @Test
    fun deleteImage() = runBlocking {
        imageDao.deleteImage(image)
        imageDao.getImages().take(1).collect {
            assertThat(it.size).isEqualTo(0)
        }
    }

    @Test
    fun deleteAllImages() = runBlocking {
        val images = listOf(image, image, image)
        imageDao.insertAll(images)
        imageDao.clearAll()
        imageDao.getImages().take(1).collect {
            assertThat(it.size).isEqualTo(0)
        }
    }
}