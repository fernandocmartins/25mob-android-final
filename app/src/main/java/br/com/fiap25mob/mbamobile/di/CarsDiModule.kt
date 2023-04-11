package br.com.fiap25mob.mbamobile.di

import br.com.fiap25mob.mbamobile.data.db.CarsDB
import br.com.fiap25mob.mbamobile.presentation.carlist.CarListViewModel
import br.com.fiap25mob.mbamobile.presentation.cars.CarsViewModel
import br.com.fiap25mob.mbamobile.repository.CarsRepository
import br.com.fiap25mob.mbamobile.repository.CarsRepositoryImpl
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSourceImpl
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSourceImpl
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val carsDiModule = module {
    viewModel { CarListViewModel(repository = get()) }

    viewModel { CarsViewModel(repository = get()) }

    factory<CarsRepository> {
        CarsRepositoryImpl(
            remoteDataSource = get(),
            localDataSource = get()
        )
    }

    factory<CarsRemoteDataSource> {
        CarsRemoteDataSourceImpl(
            firebaseConnection = FirebaseUtils(androidContext())
        )
    }

    factory<CarsLocalDataSource> {
        CarsLocalDataSourceImpl(carsDAO = get())
    }

    single { CarsDB.getInstance(androidContext()).carsDAO }
}