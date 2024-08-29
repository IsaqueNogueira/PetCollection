package com.isaquesoft.petcollection.presentation.stater

import android.content.Context
import android.content.Intent
import com.isaquesoft.petcollection.domain.PetCollectionStarter
import com.isaquesoft.petcollection.domain.entity.PetCollectionParamsEntity
import com.isaquesoft.petcollection.presentation.activity.HomePetCollectionActivity
import com.isaquesoft.petcollection.presentation.model.toModel

/**
 * Created by Isaque Nogueira on 26/08/2024
 */
class PetCollectionStarterImpl(
    private val context: Context,
) : PetCollectionStarter {
    companion object {
        const val PET_COLLECTION_PARAMS = "petCollectionParams"
    }

    override fun startPetCollection(petCollectionParams: PetCollectionParamsEntity) {
        with(context) {
            Intent(this, HomePetCollectionActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(PET_COLLECTION_PARAMS, petCollectionParams.toModel())
                startActivity(this)
            }
        }
    }
}
