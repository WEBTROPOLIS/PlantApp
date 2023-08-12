package com.example.plantapp.model.remote

import com.example.plantapp.model.local.PlantData
import retrofit2.Response
import retrofit2.http.GET


interface PlantApi {

    @GET("plantas/")
    suspend fun fetchPlantData(): Response<List<PlantData>>


    /* [funcion para detalles en un futuro ]
     Si en un futuro cambia la api aqui podriamos traer los datos para una segunda tabla
     Donde deberiamos cambiar la respuesta Response<PlantData> PlantData por la tabla nueva de detalle
     @GET("plantas/{id}")
     suspend fun fecthPlantId(@Path("id") id: Int) : Response<PlantData>
   */
}