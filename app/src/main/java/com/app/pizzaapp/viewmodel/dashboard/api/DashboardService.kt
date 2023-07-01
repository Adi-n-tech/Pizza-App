package com.app.pizzaapp.viewmodel.dashboard.api

import com.app.pizzaapp.model.PizzaDataResponse
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {

    @GET("pizza/1")
    suspend fun getPizza(): Response<PizzaDataResponse>
}