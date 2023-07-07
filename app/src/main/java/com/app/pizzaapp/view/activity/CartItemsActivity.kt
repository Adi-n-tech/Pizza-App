package com.app.pizzaapp.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.pizzaapp.model.PizzaCart
import com.app.pizzaapp.view.adaptor.CartItemsAdapter
import com.app.pizzaapp.viewmodel.cart.CartViewModel
import com.app.template.R
import com.app.template.databinding.ActivityCartItemsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartItemsBinding
    private lateinit var adapter: CartItemsAdapter
    private val cartViewModel by viewModels<CartViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart_items)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        adapter = CartItemsAdapter(emptyList(), ::onRemoveCartItem, ::onUpdateQuantity)
        binding.recycleCartItems.adapter = adapter
        //----
        cartViewModel.getAllCartItems()
        //-----
        cartViewModel.cartItems.observe(this) {
            binding.progress.visibility = View.GONE
            adapter.updateList(it)
            if (it.isEmpty()) {
                binding.emptyCart.visibility = View.VISIBLE
                binding.totalOrderValue.visibility = View.GONE
            } else {
                binding.emptyCart.visibility = View.GONE
                binding.totalOrderValue.visibility = View.VISIBLE
                var total: Long = 0
                it.forEach { total += (it.price * it.quantity) }
                binding.totalOrderValue.text = "Total Order Amount - ₹ $total"
            }
        }
    }

    private fun onRemoveCartItem(pizza: PizzaCart) {
        CoroutineScope(Dispatchers.IO).launch {
            cartViewModel.deleteCartItem(pizza.id)
        }
    }

    private fun onUpdateQuantity(pizza: PizzaCart) {
        CoroutineScope(Dispatchers.IO).launch {
            if (pizza.quantity.toInt() == 0)
                cartViewModel.deleteCartItem(pizza.id)
            else
                cartViewModel.updatePizza(pizza)
        }
    }
}