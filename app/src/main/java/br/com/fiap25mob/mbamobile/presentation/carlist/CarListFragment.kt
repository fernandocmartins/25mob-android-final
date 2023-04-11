package br.com.fiap25mob.mbamobile.presentation.carlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.databinding.FragmentCarListBinding
import br.com.fiap25mob.mbamobile.presentation.carlist.adapter.CarListAdapter
import br.com.fiap25mob.mbamobile.utils.navigateWithAnimations
import org.koin.androidx.viewmodel.ext.android.viewModel

class CarListFragment : Fragment(R.layout.fragment_car_list) {

    private var _binding: FragmentCarListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarListViewModel by viewModel()

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