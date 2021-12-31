package com.dendi.android.search_images_and_videos_app.data.video.cache

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue


/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Parcelize
data class VideosStreamsEntity(
    val large: @RawValue LargeEntity,
    val medium: @RawValue MediumEntity,
    val small: @RawValue SmallEntity,
    val tiny: @RawValue TinyEntity
): Parcelable