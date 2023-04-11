package br.com.fiap25mob.mbamobile.repository.local

import br.com.fiap25mob.mbamobile.data.dao.CarsDAO
import br.com.fiap25mob.mbamobile.data.db.CarsEntity

class CarsLocalDataSourceImpl(private val carsDAO: CarsDAO): CarsLocalDataSource {

    override suspend fun insertCar(brand: String, model: String): Long {
        val car = CarsEntity(brand = brand, model = model)
        return carsDAO.insert(car)
    }

    override suspend fun updateCar(id: Long, brand: String, model: String) {
        val car = CarsEntity(id = id, brand = brand, model = model)
        carsDAO.update(car)
    }

    override suspend fun getAllCars(): List<CarsEntity> = carsDAO.getAllCars()

    override suspend fun deleteCar(id: Long) = carsDAO.delete(id)

    override suspend fun deleteAllCars() = carsDAO.deleteAll()
}