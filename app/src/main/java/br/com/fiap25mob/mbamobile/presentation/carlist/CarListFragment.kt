package br.com.fiap25mob.mbamobile.presentation.carlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.data.dao.CarsDAO
import br.com.fiap25mob.mbamobile.data.db.CarsDB
import br.com.fiap25mob.mbamobile.databinding.FragmentCarListBinding
import br.com.fiap25mob.mbamobile.presentation.carlist.adapter.CarListAdapter
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSourceImpl
import br.com.fiap25mob.mbamobile.repository.CarsRepository
import br.com.fiap25mob.mbamobile.repository.CarsRepositoryImpl
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSourceImpl
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils
import br.com.fiap25mob.mbamobile.utils.navigateWithAnimations

class CarListFragment : Fragment(R.layout.fragment_car_list) {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val remoteDataSource: CarsRemoteDataSource = CarsRemoteDataSourceImpl(FirebaseUtils())
                val carDAO: CarsDAO = CarsDB.getInstance(requireContext()).carsDAO
                val localDataSource: CarsLocalDataSource = CarsLocalDataSourceImpl(carDAO)
                val repository: CarsRepository = CarsRepositoryImpl(
                    remoteDataSource = remoteDataSource,
                    localDataSource = localDataSource
                )
                return CarListViewModel(repository) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelEvents()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCars()
    }

    private fun observeViewModelEvents() {
        viewModel.allCarsEvent.observe(viewLifecycleOwner){ carList ->
            val carListAdapter = CarListAdapter(carList).apply {
                onItemClick = { cars ->
                    val directions = CarListFragmentDirections
                        .actionCarListFragmentToCarsFragment(cars)
                    findNavController().navigateWithAnimations(directions)
                }
            }

            with(binding.rvCars) {
                setHasFixedSize(true)
                adapter = carListAdapter
            }
        }
    }

    private fun setListeners(){
        binding.fbAddCar.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.action_carListFragment_to_carsFragment)
        }
    }
}