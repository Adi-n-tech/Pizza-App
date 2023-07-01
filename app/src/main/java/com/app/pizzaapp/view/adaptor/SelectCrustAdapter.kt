package com.app.pizzaapp.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pizzaapp.model.Crusts
import com.app.template.databinding.ItemSelectCrustBinding

class SelectCrustAdapter(var list: List<Crusts>, val onCrustSelection: (Crusts) -> Unit) :

    RecyclerView.Adapter<SelectCrustAdapter.ViewHolder>() {

    var selectedCrustId: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemSelectCrustBinding = ItemSelectCrustBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemSelectCrustBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemSelectCrustBinding.crust = list[position]
        holder.itemSelectCrustBinding.selectedCrustId = selectedCrustId
        holder.itemSelectCrustBinding.parent.setOnClickListener {
            onCrustSelection(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<Crusts>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun updateList(selectedCrustId: Long) {
        this.selectedCrustId = selectedCrustId
        notifyDataSetChanged()
    }

    class ViewHolder(val itemSelectCrustBinding: ItemSelectCrustBinding) :
        RecyclerView.ViewHolder(
            itemSelectCrustBinding.root
        )
}