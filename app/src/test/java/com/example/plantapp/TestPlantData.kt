package com.example.plantapp

import com.example.plantapp.model.local.PlantData
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestPlantData {

    private lateinit var plantData: PlantData

    @Before
    fun setup(){
        plantData = PlantData(22,"prueba","type","img","descripcion")
    }

    @After
    fun tearDown(){
        //Nada que hacer
    }

    @Test
    fun testConstructor(){
        assert(plantData.id == 22)
        assert(plantData.name == "prueba")
    }
}