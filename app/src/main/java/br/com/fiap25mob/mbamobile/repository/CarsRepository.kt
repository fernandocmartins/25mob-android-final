package br.com.fiap25mob.mbamobile.repository

import br.com.fiap25mob.mbamobile.data.db.CarsEntity

interface CarsRepository {
    suspend fun insertCar(brand: String, model: String): Long
    suspend fun updateCar(id: Long, brand: String, model: String)
    suspend fun getAllCars(): List<CarsEntity>
    suspend fun deleteCar(id: Long)
    suspend fun deleteAllCars()
}