package com.example.plantapp.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlantData::class], version = 1, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {

    abstract fun getPlantDao() : PlantDao

    companion object{
        @Volatile
        private var
                INSTANCE : PlantDatabase? = null
        fun getDataBase(context: Context) : PlantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java, "PlantDatabase")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}