package com.isaquesoft.petcollection.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Created by Isaque Nogueira on 27/08/2024
 */
@Parcelize
@Entity
data class Collection(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Long = 0L,
    val rawName: String,
    var isCollected: Boolean = false,
    var dateUpdated: Long,
) : Parcelable
