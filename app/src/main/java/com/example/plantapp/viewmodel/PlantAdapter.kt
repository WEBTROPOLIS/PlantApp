package com.example.plantapp.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantapp.R
import com.example.plantapp.databinding.ItemPlantBinding
import com.example.plantapp.model.local.PlantData
import com.example.plantapp.view.OnClickListenerPlant
import com.example.plantapp.view.PlantFragment

class PlantAdapter(
    var plants : MutableList<PlantData>,
    private var listener : OnClickListenerPlant,
    private var fragment: PlantFragment
): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    init {
        mContext = fragment.requireContext()
    }

    inner class ViewHolder(view : View):RecyclerView.ViewHolder(view){
        val mBinding = ItemPlantBinding.bind(view)

        fun setListener(plantData : PlantData){
            with(mBinding.root){
                setOnClickListener { listener.onClick(plantData.id) }
            }
        }

    }

    fun getCount(): Int {
        return plants.size
    }

    fun updateCount(newPlant : List<PlantData>){
        plants.clear()
        plants.addAll(newPlant)
        notifyDataSetChanged()
        val plantCount = getCount()
        val message = if (plantCount == 1){
            mContext.getString(R.string.count_singular)
        }else{
            mContext.getString(R.string.count_plural,plantCount)
        }
        fragment.updateCount(message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.item_plant,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = plants.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = plants[position]
        val imgUrl = plant.imgSrc
        with(holder){
            setListener(plant)
            mBinding.tvTitle.text = plant.name.uppercase()
            mBinding.tvType.text = plant.type
            Glide.with(mContext)
                .load(imgUrl)
                .fitCenter()
                .placeholder(R.drawable.ic_photo)
                .error(R.drawable.ic_no_photo)
                .into(mBinding.imgPlant)
        }
    }

}