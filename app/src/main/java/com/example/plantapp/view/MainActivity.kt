package com.example.plantapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.plantapp.model.local.PlantRepository
import com.example.plantapp.databinding.ActivityMainBinding
import com.example.plantapp.model.local.PlantDatabase
import com.example.plantapp.model.remote.RetrofitRepository
import com.example.plantapp.model.remote.viewmodel.RetrofitViewModel
import com.example.plantapp.model.remote.viewmodel.RetrofitViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var retrofitRepository: RetrofitRepository
    private lateinit var mBinding : ActivityMainBinding
    private lateinit var retrofitViewModel: RetrofitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val plantDao = PlantDatabase.getDataBase(application).getPlantDao()
        val plantRepository = PlantRepository(plantDao)

        retrofitRepository = RetrofitRepository(plantRepository)
        //inyeccion de dependencias mediante RetrofitViewModelFactory
        retrofitViewModel = ViewModelProvider( this,
            RetrofitViewModelFactory(application,retrofitRepository)
            )[RetrofitViewModel::class.java]

        retrofitViewModel.fetchAndInsertData()

        launchFragment()
    }

    private fun launchFragment(){
        supportFragmentManager.beginTransaction()
            .replace(mBinding.frame.id, PlantFragment())
            .addToBackStack(null)
            .commit()
    }
}