package com.example.horoscapp.domain

import com.example.horoscapp.domain.model.PredictionModel

interface Repository {

    suspend fun getPredictions(sign:String): PredictionModel?
}