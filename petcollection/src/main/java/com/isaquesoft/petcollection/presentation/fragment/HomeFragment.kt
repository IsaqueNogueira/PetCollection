package com.isaquesoft.petcollection.presentation.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.isaquesoft.petcollection.databinding.HomeFragmentBinding

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val args: HomeFragmentArgs by navArgs()
    private val petCollectionParams by lazy { args.petCollectionParams }
    private var adView: AdView? = null
    private var initialLayoutComplete = false
    private val adSizeMediumRectangle: AdSize
        get() {
            val display = requireActivity().windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.adViewMediumRectangle.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            val adHeight = 230
            return AdSize(adWidth, adHeight)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        MobileAds.initialize(requireContext()) {}
        adView = AdView(requireContext())
        binding.adViewMediumRectangle.addView(adView)
        binding.adViewMediumRectangle.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadAdBanner()
            }
        }
    }

    private fun loadAdBanner() {
        adView?.adUnitId = petCollectionParams.adBannerIdMediumRectangle
        adView?.setAdSize(adSizeMediumRectangle)
        val adRequest = AdRequest.Builder().build()
        adView?.loadAd(adRequest)
    }
}
