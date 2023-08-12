package com.example.plantapp.model.remote

import com.example.plantapp.model.local.PlantData
import com.example.plantapp.model.local.PlantRepository


class RetrofitRepository(
    private val plantRepository: PlantRepository
    )
        {
            private val apiService = RetrofitClient.getRetrofit()

            suspend fun fetchPlantDataAndInsert() {
                try {
                    val response = apiService.fetchPlantData()

                    if(response.isSuccessful){
                        val plantList = response.body()

                        if (plantList != null){
                            for (plant in plantList){
                                insertPlant(plant)

                            }
                            //Los Logs fueron desactivados, para las
                           // pruebas unitarios e instrumentales
                        } else {
                            // Log.d("Error", "No se encontraron datos")
                        }
                    } else {
                        // Log.d("Error", "Error en la respuesta del servidor: ${response.code()}")
                    }

                } catch (e:Exception) {
                    // Log.d("Error", "Error en la conexión: ${e.message}")
                }
            }
            suspend fun insertPlant(plant : PlantData) {
                val controlPlant = controlNullorBlankData(plant)
                plantRepository.insertPlant(controlPlant)
            }

            private fun controlNullorBlankData(plant : PlantData): PlantData {
               //Funcion para corregir datos nulos o en blanco
                return plant.copy(
                    name = plant.name.takeIf { it?.isNotBlank() == true } ?: "Sin Nombre",
                    type = plant.type.takeIf { it?.isNotBlank() == true } ?: "Sin Categoría",
                    imgSrc = plant.imgSrc.takeIf { it?.isNotBlank() == true} ?: "Sin Imagen",
                    description = plant.description.takeIf { it?.isNotBlank() == true } ?: "Sin Descripción"
                )
            }

        }