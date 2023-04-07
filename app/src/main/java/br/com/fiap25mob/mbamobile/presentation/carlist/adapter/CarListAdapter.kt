package br.com.fiap25mob.mbamobile.presentation.carlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap25mob.mbamobile.data.db.CarsEntity
import br.com.fiap25mob.mbamobile.databinding.CarItemBinding

class CarListAdapter(private val carsEntity: List<CarsEntity>): RecyclerView.Adapter<CarListAdapter.CarListViewHolder>() {

    var onItemClick: ((entity: CarsEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarListViewHolder {
        val view = CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarListViewHolder, position: Int) {
        holder.bindView(carsEntity[position])
    }

    override fun getItemCount() = carsEntity.size

   inner class CarListViewHolder(val itemBinding: CarItemBinding): RecyclerView.ViewHolder(itemBinding.root){
        private val textViewCarBrand = itemBinding.carName
        private val textViewCarModel = itemBinding.carModel

        fun bindView(carsEntity: CarsEntity){
            textViewCarBrand.text = carsEntity.brand
            textViewCarModel.text = carsEntity.model
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(carsEntity)
            }
        }
    }
}