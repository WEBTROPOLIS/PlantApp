package com.example.plantapp.model.remote.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.plantapp.model.remote.RetrofitRepository

class RetrofitViewModelFactory(
    private val application: Application,
    private val retrofitRepository: RetrofitRepository
    ) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RetrofitViewModel::class.java)){
            return  RetrofitViewModel(application,retrofitRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")

    }
}