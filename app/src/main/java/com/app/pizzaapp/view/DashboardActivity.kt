package com.app.pizzaapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.app.pizzaapp.model.PizzaDataResponse
import com.app.pizzaapp.utils.network.NetworkResult
import com.app.pizzaapp.viewmodel.dashboard.DashboardViewModel
import com.app.template.R
import com.app.template.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val dashboardViewModelNew by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        lifecycleScope.launch {
            dashboardViewModelNew.getPizzaData(this@DashboardActivity)
        }
        //----
        observeApiResult()
    }

    private fun observeApiResult() {
        dashboardViewModelNew.apiPizzaData.observe(this) {
            when (it) {
                is NetworkResult.Success -> {
                    // -----
                    val response: PizzaDataResponse = it.data as PizzaDataResponse
                    Toast.makeText(this, response.name, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    // show progress
                    Toast.makeText(this, "Loading..", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}