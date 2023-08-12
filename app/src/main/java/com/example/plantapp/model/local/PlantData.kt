package com.example.plantapp.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
/*
Debido a que ambos end-point traen la misma información
para no duplicar información y generar uso de memoria indebido
solo usaremos un end-point para almacenar la información
 */
@Entity(tableName = "plant_data")
data class PlantData (
    @PrimaryKey
    val id : Int,
    @SerializedName("nombre")
    val name : String,
    @SerializedName("tipo")
    val type : String,
    @SerializedName("imagen")
    val imgSrc : String,
    @SerializedName("descripcion")
    val description : String
)