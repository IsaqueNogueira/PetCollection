package com.isaquesoft.petcollection.presentation.model

import android.os.Parcelable
import com.isaquesoft.petcollection.domain.entity.PetCollectionParamsEntity
import kotlinx.parcelize.Parcelize

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
@Parcelize
data class PetCollectionParams(
    val adBannerIdMediumRectangle: String = "",
    val adRewardedInterstitialId: String = "",
) : Parcelable

fun PetCollectionParamsEntity.toModel() =
    PetCollectionParams(
        adBannerIdMediumRectangle = adBannerIdMediumRectangle,
        adRewardedInterstitialId = adRewardedInterstitialId,
    )
