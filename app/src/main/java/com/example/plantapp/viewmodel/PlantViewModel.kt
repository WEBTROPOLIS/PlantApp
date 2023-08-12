package com.example.plantapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.plantapp.model.local.PlantData
import com.example.plantapp.model.local.PlantDatabase
import com.example.plantapp.model.local.PlantRepository

class PlantViewModel(application: Application) :AndroidViewModel(application) {
    private val plantRepository : PlantRepository =
        PlantRepository(PlantDatabase.getDataBase(application).getPlantDao())

    fun getAllPlant() : LiveData<List<PlantData>>{
        return plantRepository.plantListLiveData
    }

    suspend fun getOnePlant(id : Int) : PlantData?{
        return plantRepository.getOnePlant(id)
    }

    suspend fun searchPlants(search: String): List<PlantData> {
        return plantRepository.searchPlants(search)
    }
}