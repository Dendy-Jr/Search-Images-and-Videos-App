package com.dendi.android.search_images_and_videos_app.core

/**
 * @author Dendy-Jr on 09.01.2022
 * olehvynnytskyi@gmail.com
 */
interface ExceptionFactory<E, S> {
    fun map(exception: E): S
}