package br.com.fiap25mob.mbamobile.presentation.cars

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.fiap25mob.mbamobile.R
import br.com.fiap25mob.mbamobile.data.dao.CarsDAO
import br.com.fiap25mob.mbamobile.data.db.CarsDB
import br.com.fiap25mob.mbamobile.databinding.FragmentCarsBinding
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSourceImpl
import br.com.fiap25mob.mbamobile.repository.CarsRepository
import br.com.fiap25mob.mbamobile.repository.CarsRepositoryImpl
import br.com.fiap25mob.mbamobile.repository.local.CarsLocalDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSource
import br.com.fiap25mob.mbamobile.repository.remote.CarsRemoteDataSourceImpl
import br.com.fiap25mob.mbamobile.utils.FirebaseUtils
import br.com.fiap25mob.mbamobile.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar

class CarsFragment : Fragment(R.layout.fragment_cars) {

    private var _binding: FragmentCarsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CarsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val remoteDataSource: CarsRemoteDataSource = CarsRemoteDataSourceImpl(FirebaseUtils())
                val carDAO: CarsDAO = CarsDB.getInstance(requireContext()).carsDAO
                val localDataSource: CarsLocalDataSource = CarsLocalDataSourceImpl(carDAO)
                val repository: CarsRepository = CarsRepositoryImpl(
                    remoteDataSource = remoteDataSource,
                    localDataSource = localDataSource
                )
                return CarsViewModel(repository) as T
            }
        }
    }

    private val args: CarsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.cars?.let {carsEntity ->  
            binding.registerCarBtn.text = getString(R.string.update)
            binding.edtCarBrand.setText(carsEntity.brand)
            binding.edtCarModel.setText(carsEntity.model)

            binding.delBtn.visibility = View.VISIBLE
        }
        observerEvents()
        initListeners()
    }

    private fun observerEvents() {
        viewModel.carStateEventData.observe(viewLifecycleOwner) { carState ->
            when(carState){
                is CarsViewModel.CarState.Included,
                is CarsViewModel.CarState.Updated,
                is CarsViewModel.CarState.Deleted -> {
                    clearEditTexts()
                    hideKeyBoard()
                    requireView().requestFocus()
                    findNavController().popBackStack()
                }
            }
        }

        viewModel.messageEventData.observe(viewLifecycleOwner) { stringResId ->
            Snackbar.make(requireView(), stringResId, Snackbar.ANIMATION_MODE_SLIDE).show()
        }
    }

    private fun clearEditTexts() {
        binding.edtCarBrand.text?.clear()
        binding.edtCarModel.text?.clear()
    }

    private fun hideKeyBoard() {
        val parentActivity = requireActivity()
        if (parentActivity is AppCompatActivity){
            parentActivity.run { hideKeyboard() }
        }
    }

    private fun dialogConfirmDelete(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.delete_confirm))
        builder
            .setPositiveButton(getString(R.string.ok_dialog)) { dialogInterface, i ->
            viewModel.deleteCar(args.cars?.id ?: 0)
            }
            .setNegativeButton(getString(R.string.cancel_dialog)) {
                    dialog: DialogInterface, which -> dialog.dismiss()
            }
        builder.show()
    }

    private fun initListeners() {
        binding.registerCarBtn.setOnClickListener {
            val brand = binding.edtCarBrand.text.toString()
            val model = binding.edtCarModel.text.toString()
            viewModel.includeUpdateCar(brand, model, args.cars?.id ?: 0)
        }

        binding.delBtn.setOnClickListener {
            dialogConfirmDelete(requireContext())
        }
    }
}