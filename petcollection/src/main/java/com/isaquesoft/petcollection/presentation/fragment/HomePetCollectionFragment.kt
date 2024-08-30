package com.isaquesoft.petcollection.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.isaquesoft.petcollection.R
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.databinding.HomePetCollectionFragmentBinding
import com.isaquesoft.petcollection.presentation.state.HomeState
import com.isaquesoft.petcollection.presentation.util.safelyNavigate
import com.isaquesoft.petcollection.presentation.util.setRawFromString
import com.isaquesoft.petcollection.presentation.view.Presets
import com.isaquesoft.petcollection.presentation.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
class HomePetCollectionFragment : Fragment() {
    private lateinit var binding: HomePetCollectionFragmentBinding
    private val args: HomePetCollectionFragmentArgs by navArgs()
    private val petCollectionParams by lazy { args.petCollectionParams }

    private val viewModel: HomeFragmentViewModel by viewModel()
    private var listCollection = mutableListOf<Collection>()

    private companion object {
        private var firsAccess = true
    }

    private lateinit var adView: AdView
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = HomePetCollectionFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getListCollection()
        setupObserver()
        setupAdMediumRectangle()
        setupAdPremiado()
        setupListener()
    }

    private fun setupAdPremiado() {
        val backgroundScope = CoroutineScope(Dispatchers.IO)
        backgroundScope.launch {
            activity?.runOnUiThread {
                loadRewardedInterstitial()
            }
        }
    }

    private fun loadRewardedInterstitial() {
        context?.let {
            RewardedInterstitialAd.load(
                it,
                petCollectionParams.adRewardedInterstitialId,
                AdRequest.Builder().build(),
                object : RewardedInterstitialAdLoadCallback() {
                    override fun onAdLoaded(ad: RewardedInterstitialAd) {
                        rewardedInterstitialAd = ad
                        binding.buttonNextAnimalHome.isEnabled = true
                        binding.buttonNextAnimalHome.setBackgroundResource(R.drawable.background_circle_contador)
                    }

                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        rewardedInterstitialAd = null
                        binding.buttonNextAnimalHome.isEnabled = false
                    }
                },
            )
        }
    }

    private fun showRewardedInterstitial(shouldFinish: Boolean = false) {
        rewardedInterstitialAd?.show(requireActivity()) {
            loadRewardedInterstitial()
        }

        callBackRewardInterstitialAd(shouldFinish)
    }

    private fun callBackRewardInterstitialAd(shouldFinish: Boolean = false) {
        if (shouldFinish && rewardedInterstitialAd == null) {
            activity?.finish()
            return
        } else if (rewardedInterstitialAd == null) {
            showKonfetti()
        }

        rewardedInterstitialAd?.fullScreenContentCallback =
            object : FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.

                    rewardedInterstitialAd = null
                    if (shouldFinish) {
                        activity?.finish()
                    } else {
                        showKonfetti()
                    }
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when ad fails to show.

                    rewardedInterstitialAd = null
                    if (shouldFinish) {
                        activity?.finish()
                    } else {
                        showKonfetti()
                    }
                }

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                }
            }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        is HomeState.ShowListCollection -> {
                            if (it.listCollection.isEmpty()) {
                                viewModel.insertFirstListCollection()
                            } else {
                                listCollection = it.listCollection.toMutableList()
                                setupTextInformationHome()
                                setupCollection()
                                viewModel.idle()
                            }
                        }

                        else -> return@collect
                    }
                }
            }
        }
    }

    private fun setupTextInformationHome() {
        if (listCollection.none { !it.isCollected }) {
            binding.textInformationHome.text =
                getString(R.string.nosso_aplicativo_gratuito_gra_as_a_voc_nsua_colecao_esta_completa)
        } else {
            binding.textInformationHome.text =
                getString(R.string.nosso_aplicativo_gratuito_gra_as_a_voc_ncolete_os_bichinhos_e_monte_a_sua_cole_o)
        }
    }

    private fun setupCollection() {
        val listNotCollected = listCollection.filter { !it.isCollected }

        if (listNotCollected.isEmpty()) {
            binding.textContadorHome.text = "${listCollection.size}/${listCollection.size}"
            binding.animationViewCollectedHome.setRawFromString(listCollection.maxByOrNull { it.dateUpdated }!!.rawName)
            binding.animationViewCollectedHome.playAnimation()
            return
        }

        if (firsAccess) {
            firsAccess = false
            showKonfetti()
            setupRandomCollection()
        } else {
            binding.textContadorHome.text =
                "${listCollection.filter { it.isCollected }.size}/${listCollection.size}"
            binding.animationViewCollectedHome.setRawFromString(
                listCollection
                    .filter {
                        it.isCollected
                    }.maxByOrNull { it.dateUpdated }!!
                    .rawName,
            )
            binding.animationViewCollectedHome.playAnimation()
        }
    }

    private fun setupRandomCollection() {
        val listNotCollected = listCollection.filter { !it.isCollected }

        if (listNotCollected.isEmpty()) return

        val randomCollection = listNotCollected.random()

        val updatedItem =
            randomCollection.copy(
                isCollected = true,
                dateUpdated = Calendar.getInstance().timeInMillis,
            )
        viewModel.updateCollection(updatedItem)

        val index = listCollection.indexOfFirst { it.id == randomCollection.id }

        if (index >= 0) {
            listCollection[index] = updatedItem
        }

        binding.textContadorHome.text =
            "${listCollection.filter { it.isCollected }.size}/${listCollection.size}"

        binding.animationViewCollectedHome.setRawFromString(randomCollection.rawName)
        binding.animationViewCollectedHome.playAnimation()
        binding.animationViewCollectedHome.repeatCount = 20
        setupTextInformationHome()
    }

    private fun setupListener() {
        with(binding) {
            imageButtonBackHome.setOnClickListener {
                showRewardedInterstitial(true)
            }

            buttonNextAnimalHome.setOnClickListener {
                showRewardedInterstitial()
                setupRandomCollection()
            }

            binding.textContadorHome.setOnClickListener {
                findNavController().safelyNavigate(HomePetCollectionFragmentDirections.actionHomeFragmentToCollectionFragment())
            }
        }
    }

    private fun setupAdMediumRectangle() {
        adView = AdView(requireContext())
        binding.adViewMediumRectangle.addView(adView)
        loadAdBanner()
    }

    private fun loadAdBanner() {
        if (::adView.isInitialized) {
            adView.adUnitId = petCollectionParams.adBannerIdMediumRectangle
            adView.setAdSize(AdSize.MEDIUM_RECTANGLE)
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
        }
    }

    private fun showKonfetti() {
        val listPresets =
            listOf(Presets.parade(), Presets.rain(), Presets.festive(), Presets.explode())
        val randomPresets = listPresets.random()
        binding.konfettiView.start(randomPresets)
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        super.onPause()
        adView.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.adViewMediumRectangle.removeAllViews()
        adView.destroy()
    }
}
