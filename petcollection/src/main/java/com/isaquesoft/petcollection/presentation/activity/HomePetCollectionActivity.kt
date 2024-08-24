package com.isaquesoft.petcollection.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.isaquesoft.petcollection.databinding.HomePetcollectionActivityBinding

/**
 * Created by Isaque Nogueira on 24/08/2024
 */
class HomePetCollectionActivity : AppCompatActivity() {
    private lateinit var binding: HomePetcollectionActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomePetcollectionActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
