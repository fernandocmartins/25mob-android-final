package br.com.fiap25mob.mbamobile.data.db

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cars_table")
data class CarsEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val brand: String? = null,
    val model: String? = null
): Parcelable