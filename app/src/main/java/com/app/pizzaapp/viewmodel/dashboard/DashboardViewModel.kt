package com.app.pizzaapp.viewmodel.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pizzaapp.model.PizzaDataResponse
import com.app.pizzaapp.utils.network.NetworkResult
import com.app.pizzaapp.viewmodel.dashboard.repository.DashboardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepositoryNew: DashboardRepository
) : ViewModel() {

    val apiPizzaData: LiveData<NetworkResult<PizzaDataResponse>> get() = dashboardRepositoryNew.piPizzaData

    fun getPizzaData(context: Context) {
        viewModelScope.launch {
            dashboardRepositoryNew.getPizza(context)
        }
    }
}