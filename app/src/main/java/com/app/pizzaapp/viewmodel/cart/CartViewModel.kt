package com.app.pizzaapp.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.pizzaapp.model.PizzaCart
import com.app.pizzaapp.viewmodel.cart.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {

    val cartItems: LiveData<List<PizzaCart>> get() = cartRepository.cartItems

    fun getAllCartItems() {
        viewModelScope.launch {
            cartRepository.getAllCartItems()
        }
    }

    suspend fun deleteCartItem(id: Long) {
        val job = viewModelScope.launch {
            cartRepository.deleteCartItem(id)
        }
        job.join()
        cartRepository.getAllCartItems()
    }

    suspend fun updatePizza(
        pizzaCart: PizzaCart
    ) {
        val job = viewModelScope.launch {
            cartRepository.updatePizza(pizzaCart)
        }
        job.join()
        cartRepository.getAllCartItems()
    }
}