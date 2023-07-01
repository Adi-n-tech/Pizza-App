package com.app.pizzaapp.viewmodel.dashboard.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.pizzaapp.model.PizzaDataResponse
import com.app.pizzaapp.utils.network.NetworkResult
import com.app.pizzaapp.viewmodel.dashboard.api.DashboardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val dashboardService: DashboardService
) {
    // api constants
    private val _apiPizzaData = MutableLiveData<NetworkResult<PizzaDataResponse>>()
    val piPizzaData: LiveData<NetworkResult<PizzaDataResponse>> get() = _apiPizzaData


    suspend fun getPizza(context: Context) {
        //handleApiResponse({ dashboardService.getPizza() }, _apiPizzaData)
    }
}