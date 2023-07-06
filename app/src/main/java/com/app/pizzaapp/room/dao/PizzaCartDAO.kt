package com.app.pizzaapp.room.dao

import androidx.room.*
import com.app.pizzaapp.model.PizzaCart

@Dao
interface PizzaCartDAO {
    @Upsert
    suspend fun insertPizzaToCart(pizzaCart: PizzaCart)

    @Query("SELECT * FROM PizzaCart")
    suspend fun getAllCartItems(): List<PizzaCart>

}