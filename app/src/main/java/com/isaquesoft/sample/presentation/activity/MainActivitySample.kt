package com.isaquesoft.sample.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.MobileAds
import com.isaquesoft.petcollection.domain.PetCollectionStarter
import com.isaquesoft.petcollection.domain.entity.PetCollectionParamsEntity
import org.koin.android.ext.android.inject

/**
 * Created by Isaque Nogueira on 23/08/2024
 */
class MainActivitySample : AppCompatActivity() {
    private val petCollectionStarter: PetCollectionStarter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        val petCollectionParams =
            PetCollectionParamsEntity(
//                adBannerId = "ca-app-pub-6470587668575312/1303668027",
//                adBannerIdMediumRectangle = "ca-app-pub-6470587668575312/1303668027",
//                adRewardedInterstitialId = "ca-app-pub-6470587668575312/2812190815",
            )
        petCollectionStarter.startPetCollection(petCollectionParams)
        finish()
    }
}
