package com.app.pizzaapp.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.pizzaapp.model.PizzaCart
import com.app.pizzaapp.room.dao.PizzaCartDAO

@Database(entities = [PizzaCart::class], version = 1)
abstract class PizzaCartDatabase : RoomDatabase() {
    abstract fun pizzaCartDao(): PizzaCartDAO
}