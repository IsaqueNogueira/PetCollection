package com.isaquesoft.petcollection.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.isaquesoft.petcollection.R
import com.isaquesoft.petcollection.databinding.HomePetcollectionActivityBinding
import com.isaquesoft.petcollection.presentation.model.PetCollectionParams
import com.isaquesoft.petcollection.presentation.stater.PetCollectionStarterImpl.Companion.PET_COLLECTION_PARAMS

/**
 * Created by Isaque Nogueira on 24/08/2024
 */
class HomePetCollectionActivity : AppCompatActivity() {
    private lateinit var binding: HomePetcollectionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePetcollectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupStatusBarColor()
        setupGetParams()
    }

    private fun setupGetParams() {
        val petCollectionParams: PetCollectionParams =
            intent.getParcelableExtra(PET_COLLECTION_PARAMS)
                ?: throw IllegalArgumentException("Where is Pet colletion params?")

        setupNavigation(petCollectionParams)
    }

    private fun setupNavigation(it: PetCollectionParams) {
        val navController =
            Navigation.findNavController(this, R.id.navHostHomePetCollectionActivity)
        val bundle =
            Bundle().apply {
                putParcelable(PET_COLLECTION_PARAMS, it)
            }

        navController.setGraph(R.navigation.nav_graph_pet_collection, bundle)
    }

    private fun setupStatusBarColor() {
        val window = window
        val statusBarColor = getColor(R.color.color_orange_pet_collection)
        window.statusBarColor = statusBarColor
    }
}
