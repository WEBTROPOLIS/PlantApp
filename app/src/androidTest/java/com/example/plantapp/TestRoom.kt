package com.example.plantapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.plantapp.model.local.PlantDao
import com.example.plantapp.model.local.PlantData
import com.example.plantapp.model.local.PlantDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestRoom {
    private lateinit var mDao : PlantDao
    private lateinit var db : PlantDatabase

    @Before
    fun setup(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,PlantDatabase::class.java).build()
        mDao = db.getPlantDao()
    }

    @After
    fun shutDown(){
        db.close()
    }

    @Test
    fun insertPlantTest() = runBlocking {
        val newPlant = PlantData(20,"test","type","img","description")
        mDao.insertPlant(newPlant)
        val mPlant = mDao.getAllPlants()
        val mPlantList : List<PlantData> = mPlant.value?: emptyList()

        assert(mPlantList.isNotEmpty())
        assert(mPlantList.size == 1)
        assert(mPlantList[0].id==20)

    }



}