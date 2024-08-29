package com.isaquesoft.petcollection.presentation.model

import android.os.Parcelable
import com.isaquesoft.petcollection.domain.entity.PetCollectionParamsEntity
import kotlinx.parcelize.Parcelize

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
@Parcelize
data class PetCollectionParams(
    val adBannerId: String = "",
    val adBannerIdMediumRectangle: String = "",
    val adRewardedInterstitialId: String = "",
) : Parcelable

fun PetCollectionParamsEntity.toModel() =
    PetCollectionParams(
        adBannerId = adBannerId,
        adBannerIdMediumRectangle = adBannerIdMediumRectangle,
        adRewardedInterstitialId = adRewardedInterstitialId,
    )
