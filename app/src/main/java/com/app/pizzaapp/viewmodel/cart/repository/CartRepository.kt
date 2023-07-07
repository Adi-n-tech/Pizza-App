package com.app.pizzaapp.viewmodel.cart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.pizzaapp.model.PizzaCart
import com.app.pizzaapp.room.db.PizzaCartDatabase
import com.app.pizzaapp.viewmodel.dashboard.api.DashboardService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val pizzaCartDatabase: PizzaCartDatabase
) {
    private val _cartItems = MutableLiveData<List<PizzaCart>>()
    val cartItems: LiveData<List<PizzaCart>> get() = _cartItems


    suspend fun getAllCartItems() {
        _cartItems.postValue(pizzaCartDatabase.pizzaCartDao().getAllCartItems())
    }

    suspend fun deleteCartItem(id: Long) {
        pizzaCartDatabase.pizzaCartDao().deleteCartItemById(id)
    }

    suspend fun updatePizza(pizzaCart: PizzaCart) {
        pizzaCartDatabase.pizzaCartDao().updatePizzaQuantity(pizzaCart)
    }
}