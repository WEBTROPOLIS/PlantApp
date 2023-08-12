package com.example.plantapp.model.remote.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantapp.model.remote.RetrofitRepository
import kotlinx.coroutines.launch

class RetrofitViewModel(
    application: Application,
    private val retrofitRepository: RetrofitRepository
    ) : AndroidViewModel(application)

    {
        fun fetchAndInsertData() {
            viewModelScope.launch {
                retrofitRepository.fetchPlantDataAndInsert()
            }
        }

    }