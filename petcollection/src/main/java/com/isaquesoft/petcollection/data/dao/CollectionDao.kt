package com.isaquesoft.petcollection.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.isaquesoft.petcollection.data.model.Collection

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
@Dao
interface CollectionDao {
    @Insert
    suspend fun insertCollection(collection: Collection): Long

    @Insert
    suspend fun insertListCollection(collection: List<Collection>): List<Long>

    @Query("SELECT * FROM Collection")
    suspend fun getListCollection(): List<Collection>

    @Query("SELECT * FROM Collection WHERE id=:id ")
    suspend fun getCollection(id: Int): Collection

    @Update
    suspend fun updateCollection(collection: Collection)

    @Delete
    suspend fun deleteCollection(collection: Collection)
}
