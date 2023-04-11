package br.com.fiap25mob.mbamobile.repository.remote

import br.com.fiap25mob.mbamobile.data.db.CarsEntity
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils

class CarsRemoteDataSourceImpl(
    private var firebaseConnection: FirebaseUtils
): CarsRemoteDataSource {
    override suspend fun insertCar(id: Long, brand: String, model: String) {
        firebaseConnection.saveValue(id, brand, model)
    }

    override suspend fun getAllCars(): List<CarsEntity> {
        return firebaseConnection.readValues()
    }

    override suspend fun deleteCar(id: Long) {
        firebaseConnection.deleteValue(id)
    }

    override suspend fun deleteAllCars() {
        firebaseConnection.deleteAllValues()
    }
}