package com.app.pizzaapp.view.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.pizzaapp.model.Sizes
import com.app.template.databinding.ItemSelectSizeBinding

class SelectSizeAdapter(var list: List<Sizes>,val onSizeSelection: (Sizes) -> Unit) :

    RecyclerView.Adapter<SelectSizeAdapter.ViewHolder>() {

    var selectedSizeId: Long = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemSelectSizeBinding = ItemSelectSizeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemSelectSizeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemSelectSizeBinding.size = list[position]
        holder.itemSelectSizeBinding.selectedSizeId = selectedSizeId
        holder.itemSelectSizeBinding.parent.setOnClickListener {
            onSizeSelection(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<Sizes>){
        this.list=list
        notifyDataSetChanged()
    }

      fun updateList(selectedSizeId: Long){
        this.selectedSizeId=selectedSizeId
        notifyDataSetChanged()
    }

    class ViewHolder(val itemSelectSizeBinding: ItemSelectSizeBinding) :
        RecyclerView.ViewHolder(
            itemSelectSizeBinding.root
        )
}