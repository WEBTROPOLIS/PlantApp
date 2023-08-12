package com.example.plantapp.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.plantapp.model.local.PlantData

@Dao
interface PlantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant : PlantData)

    @Query("SELECT * FROM plant_data ORDER BY name")
    fun getAllPlants():LiveData<List<PlantData>>

    @Query("SELECT * FROM plant_data WHERE id = :idPlant")
    suspend fun getOnePlant(idPlant : Int) : PlantData?

    @Query("SELECT * FROM plant_data WHERE name LIKE  :search || '%' OR type LIKE  :search || '%' ORDER BY name,type ")
    suspend fun searchPlants(search: String): List<PlantData>
}