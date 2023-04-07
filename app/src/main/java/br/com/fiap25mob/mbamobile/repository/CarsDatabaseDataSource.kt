package br.com.fiap25mob.mbamobile.repository

import br.com.fiap25mob.mbamobile.data.dao.CarsDAO
import br.com.fiap25mob.mbamobile.data.db.CarsEntity

class CarsDatabaseDataSource(private val carsDAO: CarsDAO): CarsRepository {

    override suspend fun insertCar(brand: String, model: String): Long {
        val car = CarsEntity(brand = brand, model = model)
        return carsDAO.insert(car)
    }

    override suspend fun updateCar(id: Long, brand: String, model: String) {
        val car = CarsEntity(id = id, brand = brand, model = model)
        carsDAO.update(car)
    }

    override suspend fun getAllCars(): List<CarsEntity> {
        return carsDAO.getAllCars()
    }

    override suspend fun deleteCar(id: Long) {
        carsDAO.delete(id)
    }

    override suspend fun deleteAllCars() {
        carsDAO.deleteAll()
    }
}