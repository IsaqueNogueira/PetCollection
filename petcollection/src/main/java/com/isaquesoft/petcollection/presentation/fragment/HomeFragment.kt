package com.isaquesoft.petcollection.presentation.fragment

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.isaquesoft.geradorderecibos.presentation.util.safelyNavigate
import com.isaquesoft.petcollection.R
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.databinding.HomeFragmentBinding
import com.isaquesoft.petcollection.presentation.state.HomeState
import com.isaquesoft.petcollection.presentation.util.setRawFromString
import com.isaquesoft.petcollection.presentation.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
class HomeFragment : Fragment() {
    private lateinit var binding: HomeFragmentBinding
    private val args: HomeFragmentArgs by navArgs()
    private val petCollectionParams by lazy { args.petCollectionParams }

    private val viewModel: HomeFragmentViewModel by viewModel()
    private var listCollection = emptyList<Collection>()

    private companion object {
        var firsAccess = true
    }

    private lateinit var adView: AdView
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
        viewModel.getListCollection()
        setupObserver()
        setupAdMediumRectangle()
        setupListener()
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
                                listCollection = it.listCollection
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

    private fun setupCollection() {
        val listNotCollected = listCollection.filter { !it.isCollected }

        if (listNotCollected.isEmpty()) {
            binding.textContadorHome.text = "${listCollection.size}/${listCollection.size}"
            binding.animationViewCollectedHome.setRawFromString(listCollection.maxByOrNull { it.dateUpdated }!!.rawName)
            return
        }

        if (firsAccess) {
            firsAccess = false
            setupRandomCollection()
        } else {
            binding.textContadorHome.text =
                "${listCollection.filter { it.isCollected }.size}/${listCollection.size}"
            binding.animationViewCollectedHome.setRawFromString(
                listCollection
                    .filter {
                        it.isCollected
                    }.sortedByDescending { it.dateUpdated }
                    .first()
                    .rawName,
            )
        }
    }

    private fun setupRandomCollection() {
        val listNotCollected = listCollection.filter { !it.isCollected }
        binding.textContadorHome.text =
            "${listCollection.filter { it.isCollected }.size + 1}/${listCollection.size}"

        val randomCollection = listNotCollected.random()
        viewModel.updateCollection(
            randomCollection.copy(
                isCollected = true,
                dateUpdated = Calendar.getInstance().timeInMillis,
            ),
        )

        binding.animationViewCollectedHome.setRawFromString(randomCollection.rawName)
        binding.animationViewCollectedHome.repeatCount = 10
    }

    private fun setupListener() {
        with(binding) {
            imageButtonBackHome.setOnClickListener {
                activity?.finish()
            }

            lifecycleScope.launch {
                delay(2500)
                buttonNextAnimalHome.isEnabled = true
                buttonNextAnimalHome.setBackgroundResource(R.drawable.background_circle_contador)
            }

            buttonNextAnimalHome.setOnClickListener {
                setupRandomCollection()
            }

            binding.textContadorHome.setOnClickListener {
                findNavController().safelyNavigate(HomeFragmentDirections.actionHomeFragmentToCollectionFragment())
            }
        }
    }

    private fun setupAdMediumRectangle() {
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
        adView.adUnitId = petCollectionParams.adBannerIdMediumRectangle
        adView.setAdSize(adSizeMediumRectangle)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        super.onPause()
        adView.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        adView.destroy()
    }
}
