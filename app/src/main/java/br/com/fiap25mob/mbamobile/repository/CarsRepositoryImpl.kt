package br.com.fiap25mob.mbamobile.repository

import br.com.fiap25mob.mbamobile.data.db.CarsEntity
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSource

class CarsRepositoryImpl(
    private val remoteDataSource: CarsRemoteDataSource,
    private val localDataSource: CarsLocalDataSource
): CarsRepository {
    override suspend fun insertCar(brand: String, model: String): Long {
        val id = localDataSource.insertCar(brand, model)
        if (id > 0) remoteDataSource.insertCar(id, brand, model)
        return id
    }

    override suspend fun updateCar(id: Long, brand: String, model: String) {
        localDataSource.updateCar(id, brand, model)
        remoteDataSource.insertCar(id, brand, model)
    }

    override suspend fun getAllCars(): List<CarsEntity> {
        return remoteDataSource.getAllCars().ifEmpty { localDataSource.getAllCars() }
    }

    override suspend fun deleteCar(id: Long) {
        localDataSource.deleteCar(id)
        remoteDataSource.deleteCar(id)
    }

    override suspend fun deleteAllCars() {
        localDataSource.deleteAllCars()
        remoteDataSource.deleteAllCars()
    }
}