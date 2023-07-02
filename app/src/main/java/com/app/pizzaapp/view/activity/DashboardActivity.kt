package com.app.pizzaapp.view.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.app.pizzaapp.model.Crusts
import com.app.pizzaapp.model.PizzaDataResponse
import com.app.pizzaapp.model.Sizes
import com.app.pizzaapp.utils.network.NetworkResult
import com.app.pizzaapp.view.adaptor.SelectCrustAdapter
import com.app.pizzaapp.view.adaptor.SelectSizeAdapter
import com.app.pizzaapp.viewmodel.dashboard.DashboardViewModel
import com.app.template.R
import com.app.template.databinding.ActivityDashboardBinding
import com.app.template.databinding.DialogAddCustomPizzaBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var selectCrustAdapter: SelectCrustAdapter
    private lateinit var selectSizeAdapter: SelectSizeAdapter
    private val dashboardViewModelNew by viewModels<DashboardViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        lifecycleScope.launch {
            dashboardViewModelNew.getPizzaData(this@DashboardActivity)
        }
        //----
        observeApiResult()
        //-----
        binding.btnAddCart.setOnClickListener {
            showAddToCartDialog()
        }
        selectCrustAdapter = SelectCrustAdapter(emptyList(), ::onCrustSelection)
        selectSizeAdapter = SelectSizeAdapter(emptyList(), ::onSizeSelection)
    }

    private fun observeApiResult() {
        dashboardViewModelNew.apiPizzaData.observe(this) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.dataCard.visibility = View.VISIBLE
                    // -----
                    val response = it.data as? PizzaDataResponse
                    response?.let { data ->
                        binding.name.text = data.name
                        binding.description.text = data.description
                        val vegDrawable = getDrawable(R.drawable.veg_icon)
                        val nonVegDrawable = getDrawable(R.drawable.non_veg)
                        binding.vegIcon.setImageDrawable(if (data.isVeg == true) vegDrawable else nonVegDrawable)
                        selectCrustAdapter.updateList(data.crusts)
                        selectCrustAdapter.updateList(data.defaultCrust)
                        val defaultCrust = data.crusts.find { it.id == data.defaultCrust }
                        val defaultSize =
                            defaultCrust?.sizes?.find { it.id == defaultCrust.defaultSize }
                        defaultCrust?.sizes?.let { sizes ->
                            selectSizeAdapter.updateList(sizes)
                            defaultSize?.id?.let(selectSizeAdapter::updateList)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    binding.progress.visibility = View.GONE
                    binding.dataCard.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    binding.dataCard.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onCrustSelection(crust: Crusts) {
        selectCrustAdapter.updateList(crust.id)
        selectSizeAdapter.updateList(crust.sizes)
        selectSizeAdapter.updateList(crust.defaultSize)
    }

    private fun onSizeSelection(size: Sizes) {
        selectSizeAdapter.updateList(size.id)
    }

    private fun showAddToCartDialog() {
        val dialog = Dialog(this)
        val binding: DialogAddCustomPizzaBinding =
            DialogAddCustomPizzaBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.setCancelable(true)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.recyclerSelectCrust.adapter = selectCrustAdapter
        binding.recyclerSelectSize.adapter = selectSizeAdapter
        dialog.show()
    }
}