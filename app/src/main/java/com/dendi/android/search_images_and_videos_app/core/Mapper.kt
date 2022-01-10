package com.dendi.android.search_images_and_videos_app.core

/**
 * @author Dendy-Jr on 05.01.2022
 * olehvynnytskyi@gmail.com
 */
interface Mapper<S, R> {
    suspend fun map(from: S): R
}

fun <S, R> Mapper<S, R>.forList(): suspend (List<S>) -> List<R> {
    return { list -> list.map { item -> map(item) } }
}