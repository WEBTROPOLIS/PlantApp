package com.example.plantapp.model.local

import androidx.lifecycle.LiveData


class PlantRepository(private val plantDao: PlantDao) {

    val plantListLiveData = plantDao.getAllPlants()

    suspend fun insertPlant(plant : PlantData) { plantDao.insertPlant(plant)}

    suspend fun getOnePlant(id : Int) : PlantData? {
        return plantDao.getOnePlant(id)
    }

    suspend fun searchPlants(search: String): List<PlantData> {
        return plantDao.searchPlants(search)
    }

}