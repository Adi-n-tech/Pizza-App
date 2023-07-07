package com.app.pizzaapp.room.dao

import androidx.room.*
import com.app.pizzaapp.model.PizzaCart

@Dao
interface PizzaCartDAO {

    @Upsert
    suspend fun updatePizzaToCart(pizzaCart: PizzaCart) {
        val existingPizzaCart = getCartItemById(pizzaCart.id)

        if (existingPizzaCart != null) {
            pizzaCart.quantity += existingPizzaCart.quantity
        }
        insertPizzaToCart(pizzaCart)
    }

    @Upsert
    suspend fun updatePizzaQuantity(pizzaCart: PizzaCart)

    @Upsert
    suspend fun insertPizzaToCart(pizzaCart: PizzaCart)

    @Query("SELECT * FROM PizzaCart WHERE id=:id")
    suspend fun getCartItemById(id: Long): PizzaCart

    @Query("DELETE FROM PizzaCart WHERE id=:id")
    suspend fun deleteCartItemById(id: Long)

    @Query("SELECT * FROM PizzaCart")
    suspend fun getAllCartItems(): List<PizzaCart>
}