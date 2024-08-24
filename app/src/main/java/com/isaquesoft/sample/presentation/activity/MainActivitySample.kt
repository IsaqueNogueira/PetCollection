package com.isaquesoft.sample.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.isaquesoft.petcollection.presentation.activity.HomePetCollectionActivity

/**
 * Created by Isaque Nogueira on 23/08/2024
 */
class MainActivitySample : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this, HomePetCollectionActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}
