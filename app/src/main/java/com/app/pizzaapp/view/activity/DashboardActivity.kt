package com.app.pizzaapp.view.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.app.pizzaapp.model.Crusts
import com.app.pizzaapp.model.PizzaCart
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
    private lateinit var pizzaData: PizzaDataResponse
    private lateinit var selectCrustAdapter: SelectCrustAdapter
    private lateinit var selectSizeAdapter: SelectSizeAdapter
    private val dashboardViewModel by viewModels<DashboardViewModel>()
    private lateinit var selectedCrust: Crusts
    private lateinit var selectedSize: Sizes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)

        lifecycleScope.launch {
            dashboardViewModel.getPizzaData(this@DashboardActivity)
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
        dashboardViewModel.apiPizzaData.observe(this) { it ->
            when (it) {
                is NetworkResult.Success -> {
                    binding.progress.visibility = View.GONE
                    binding.dataCard.visibility = View.VISIBLE
                    // -----
                    val response = it.data
                    response?.let { data ->
                        pizzaData = data
                        binding.name.text = data.name
                        binding.description.text = data.description
                        val vegDrawable = getDrawable(R.drawable.veg_icon)
                        val nonVegDrawable = getDrawable(R.drawable.non_veg)
                        binding.vegIcon.setImageDrawable(if (data.isVeg == true) vegDrawable else nonVegDrawable)
                        selectCrustAdapter.updateList(data.crusts)
                        selectCrustAdapter.updateList(data.defaultCrust)
                        val defaultCrust = data.crusts.find { it.id == data.defaultCrust }
                        if (defaultCrust != null) {
                            selectedCrust = defaultCrust
                        }
                        val defaultSize =
                            defaultCrust?.sizes?.find { it.id == defaultCrust.defaultSize }
                        if (defaultSize != null) {
                            selectedSize = defaultSize
                        }
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
        selectedCrust = crust
        selectCrustAdapter.updateList(crust.id)
        selectSizeAdapter.updateList(crust.sizes)
        selectSizeAdapter.updateList(crust.defaultSize)
    }

    private fun onSizeSelection(size: Sizes) {
        selectedSize = size
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
        binding.btnAddCart.setOnClickListener {
            val pizzaCart = PizzaCart(
                id = "${selectedCrust.id}${selectedSize.id}".toLong(),
                name = pizzaData.name,
                isVeg = pizzaData.isVeg,
                description = pizzaData.description,
                crustName = selectedCrust.name,
                sizeName = selectedSize.name,
                price = selectedSize.price,
                quantity = 1
            )
            dashboardViewModel.addPizzaToCart(pizzaCart)
            dialog.dismiss()
            Toast.makeText(this, "${pizzaData.name} pizza added to cart !!", Toast.LENGTH_SHORT)
                .show()
        }
        dialog.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_cart -> {
                startActivity(Intent(this, CartItemsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}