package com.isaquesoft.petcollection.data.repository

import com.isaquesoft.petcollection.data.dao.CollectionDao
import com.isaquesoft.petcollection.data.model.Collection
import com.isaquesoft.petcollection.util.COLLECTION_SIZE
import java.util.Calendar

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
class CollectionRepository(
    private val collectionDao: CollectionDao,
) {
    suspend fun insertCollection(collection: Collection) = collectionDao.insertCollection(collection)

    suspend fun insertListCollection(collection: List<Collection>) = collectionDao.insertListCollection(collection)

    suspend fun getListCollection(): List<Collection> = collectionDao.getListCollection()

    suspend fun getCollection(id: Int): Collection = collectionDao.getCollection(id)

    suspend fun updateCollection(collection: Collection) = collectionDao.updateCollection(collection)

    suspend fun deleteCollection(collection: Collection) = collectionDao.deleteCollection(collection)

    fun createListCollection(): List<Collection> {
        val list = mutableListOf<Collection>()

        val calendar = Calendar.getInstance()

        for (i in 1..COLLECTION_SIZE) {
            list.add(Collection(rawName = "a$i", dateUpdated = calendar.timeInMillis))
        }

        return list
    }
}
