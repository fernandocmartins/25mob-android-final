package br.com.fiap25mob.mbamobile.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.fiap25mob.mbamobile.data.dao.CarsDAO

@Database(entities = [CarsEntity::class], version = 1)
abstract class CarsDB: RoomDatabase() {

    abstract val carsDAO: CarsDAO
    companion object{
        @Volatile
        private var INSTANCE: CarsDB? = null

        fun getInstance(context: Context): CarsDB {
            synchronized(this) {
                var instance: CarsDB? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        CarsDB::class.java,
                        "cars_database"
                    ).build()
                }
                return instance
            }
        }
    }
}