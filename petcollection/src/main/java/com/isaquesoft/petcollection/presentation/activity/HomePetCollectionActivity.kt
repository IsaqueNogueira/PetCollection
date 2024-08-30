package com.isaquesoft.petcollection.presentation.activity

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.isaquesoft.petcollection.R
import com.isaquesoft.petcollection.databinding.HomePetcollectionActivityBinding
import com.isaquesoft.petcollection.presentation.model.PetCollectionParams
import com.isaquesoft.petcollection.presentation.stater.PetCollectionStarterImpl.Companion.PET_COLLECTION_PARAMS

/**
 * Created by Isaque Nogueira on 24/08/2024
 */
class HomePetCollectionActivity : AppCompatActivity() {
    private lateinit var binding: HomePetcollectionActivityBinding

    private lateinit var adView: AdView
    private var initialLayoutComplete = false

    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.adViewBanner.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePetcollectionActivityBinding.inflate(layoutInflater)
        window.statusBarColor = Color.parseColor("#E45F50")
        setContentView(binding.root)
        adView = AdView(this)
        setupGetParams()
    }

    private fun setupGetParams() {
        val petCollectionParams: PetCollectionParams =
            intent.getParcelableExtra(PET_COLLECTION_PARAMS)
                ?: throw IllegalArgumentException("Where is Pet colletion params?")

        petCollectionParams.let {
            binding.adViewBanner.addView(adView)
            binding.adViewBanner.viewTreeObserver.addOnGlobalLayoutListener {
                if (!initialLayoutComplete) {
                    initialLayoutComplete = true
                    loadAdBanner(it)
                }
            }

            setupNavigation(it)
        }
    }

    private fun setupNavigation(it: PetCollectionParams) {
        val navController = Navigation.findNavController(this, R.id.navHostMainActivityPetCollection)
        val bundle =
            Bundle().apply {
                putParcelable("petCollectionParams", it)
            }

        navController.setGraph(R.navigation.nav_graph_pet_collection, bundle)
    }

    private fun loadAdBanner(petCollectionParams: PetCollectionParams) {
        adView.adUnitId = petCollectionParams.adBannerId
        adView.setAdSize(adSize)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
