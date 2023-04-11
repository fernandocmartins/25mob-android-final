package br.com.fiap25mob.mbamobile.presentation.carlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap25mob.mbamobile.data.db.CarsEntity
import br.com.fiap25mob.mbamobile.repository.CarsRepository
import kotlinx.coroutines.launch

class CarListViewModel(private val repository: CarsRepository) : ViewModel() {

    private val _allCarsEvent = MutableLiveData<List<CarsEntity>>()
    val allCarsEvent: LiveData<List<CarsEntity>>get() = _allCarsEvent

    fun getCars() = viewModelScope.launch {
        _allCarsEvent.postValue(repository.getAllCars())
    }
}