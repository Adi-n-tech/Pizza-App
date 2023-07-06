package com.app.pizzaapp.viewmodel.dashboard.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.pizzaapp.model.PizzaCart
import com.app.pizzaapp.model.PizzaDataResponse
import com.app.pizzaapp.room.db.PizzaCartDatabase
import com.app.pizzaapp.utils.Utility.readJsonFromAsset
import com.app.pizzaapp.utils.network.NetworkResult
import com.app.pizzaapp.viewmodel.dashboard.api.DashboardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val dashboardService: DashboardService,
    private val pizzaCartDatabase: PizzaCartDatabase
) {
    // api constants
    private val _apiPizzaData = MutableLiveData<NetworkResult<PizzaDataResponse>>()
    val piPizzaData: LiveData<NetworkResult<PizzaDataResponse>> get() = _apiPizzaData


    suspend fun getPizza(context: Context) {
        _apiPizzaData.postValue(NetworkResult.Loading())
        val response = readJsonFromAsset<PizzaDataResponse>(context, "pizza_api_mock.json")
        if (response != null) {
            _apiPizzaData.postValue(NetworkResult.Success(response))
        }
        //handleApiResponse({ dashboardService.getPizza() }, _apiPizzaData)
    }

    suspend fun addPizzaToCart(pizzaCart: PizzaCart) {
        pizzaCartDatabase.pizzaCartDao().insertPizzaToCart(pizzaCart)
    }
}