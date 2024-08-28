package com.isaquesoft.petcollection.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.isaquesoft.petcollection.data.dao.CollectionDao
import com.isaquesoft.petcollection.data.model.Collection

/**
 * Created by Isaque Nogueira on 27/08/2024
 */

@Database(
    entities = [Collection::class],
    version = 1,
)
abstract class CollectionDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao

    companion object {
        @Volatile
        private var instance: CollectionDatabase? = null

        fun getDatabase(context: Context): CollectionDatabase =
            instance ?: synchronized(this) {
                instance ?: Room
                    .databaseBuilder(
                        context,
                        CollectionDatabase::class.java,
                        "collectionDb",
                    ).build()
                    .also {
                        instance = it
                    }
            }
    }
}
