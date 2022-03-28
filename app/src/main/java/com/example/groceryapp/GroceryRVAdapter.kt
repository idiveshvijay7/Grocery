package com.example.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grocery_rv_item.view.*

class GroceryRVAdapter(
    var list: List<GroceryItems>,
    val groceryitemclickedinterface: Groceryitemclickedinterface
) :
    RecyclerView.Adapter<GroceryRVAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


    interface Groceryitemclickedinterface {
        fun onItemClick(grocery: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_item, parent, false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        holder.itemView.IdTVitemname.text = list.get(position).itemName
        holder.itemView.IdTVQuantity.text = list.get(position).itemQuantity.toString()
        holder.itemView.IdTVrate.text = "Rs" + list.get(position).itemPrice.toString()
        val itemtotal: Int = list.get(position).itemPrice * list.get(position).itemQuantity

        holder.itemView.idTVtotalamt.text = "Rs" + itemtotal.toString()
        holder.itemView.idTVDelete.setOnClickListener {
            groceryitemclickedinterface.onItemClick((list.get(position)))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

