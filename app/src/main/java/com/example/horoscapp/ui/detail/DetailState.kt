package com.example.horoscapp.ui.detail

import com.example.horoscapp.domain.model.HoroscopeModel

sealed class DetailState {
    data object Loading : DetailState()
    data class Error(val error: String) : DetailState()
    data class Success(val prediction: String, val sign:String, val horoscopeModel: HoroscopeModel) : DetailState()
}