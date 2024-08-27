package com.isaquesoft.petcollection.domain

import com.isaquesoft.petcollection.domain.entity.PetCollectionParamsEntity

/**
 * Created by Isaque Nogueira on 24/08/2024
 */
interface PetCollectionStarter {
    fun startPetCollection(petCollectionParams: PetCollectionParamsEntity)
}
