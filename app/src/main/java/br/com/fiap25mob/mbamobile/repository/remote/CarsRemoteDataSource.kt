package br.com.fiap25mob.mbamobile.repository.remote

import br.com.fiap25mob.mbamobile.data.db.CarsEntity

interface CarsRemoteDataSource {
    suspend fun insertCar(id: Long, brand: String, model: String)
    suspend fun getAllCars(): List<CarsEntity>
    suspend fun deleteCar(id: Long)
    suspend fun deleteAllCars()
}