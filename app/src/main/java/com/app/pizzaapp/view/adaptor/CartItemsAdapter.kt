package com.app.pizzaapp.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pizzaapp.model.PizzaCart
import com.app.template.databinding.ItemCartBinding

class CartItemsAdapter(
    var list: List<PizzaCart>,
    val onRemoveCartItem: (PizzaCart) -> Unit,
    val onUpdateQuantity: (PizzaCart) -> Unit
) :

    RecyclerView.Adapter<CartItemsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemCartBinding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemCartBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemCartBinding.name.text = "${item.name} - ${item.sizeName} ${item.crustName}"
        holder.itemCartBinding.quantity.text = item.quantity.toString()
        holder.itemCartBinding.price.text = "₹${item.price * item.quantity} /-"
        holder.itemCartBinding.remove.setOnClickListener {
            onRemoveCartItem(item)
        }

        holder.itemCartBinding.quantityIncrease.setOnClickListener {
            item.quantity = item.quantity + 1
            onUpdateQuantity(item)
        }

        holder.itemCartBinding.decreaseQuentity.setOnClickListener {
            item.quantity = item.quantity - 1
            onUpdateQuantity(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(it: List<PizzaCart>?) {
        if (it != null) {
            this.list = it
        }
        notifyDataSetChanged()
    }


    class ViewHolder(val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(
            itemCartBinding.root
        )
}