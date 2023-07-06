package com.app.pizzaapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PizzaCart")
data class PizzaCart(
    @PrimaryKey val id: Long,
    var name: String? = null,
    var isVeg: Boolean? = null,
    var description: String? = null,
    var crustName: String? = null,
    var sizeName: String? = null,
    var price: Long
)
