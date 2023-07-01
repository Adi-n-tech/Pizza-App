package com.app.pizzaapp.model

import com.google.gson.annotations.SerializedName

data class PizzaDataResponse(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("isVeg") var isVeg: Boolean? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("defaultCrust") var defaultCrust: Long,
    @SerializedName("crusts") var crusts: ArrayList<Crusts> = arrayListOf()
)


data class Sizes(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String? = null,
    @SerializedName("price") var price: Long
)

data class Crusts(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String? = null,
    @SerializedName("defaultSize") var defaultSize: Long,
    @SerializedName("sizes") var sizes: ArrayList<Sizes> = arrayListOf()
)