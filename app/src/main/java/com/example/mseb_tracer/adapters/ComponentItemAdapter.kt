package com.example.mseb_tracer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.mseb_tracer.R
import java.util.*
import kotlin.collections.ArrayList

class ComponentItemAdapter (protected val itemList : ArrayList<String>) : RecyclerView.Adapter<ComponentItemAdapter.MyViewHolder>(){

    private lateinit var mListner : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int);
    }

    fun setOnItemClickedListner(listner : onItemClickListener){
        mListner = listner
    }

    class MyViewHolder(itemView : View, listner : onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val itemButton : Button = itemView.findViewById(R.id.component_item_list_button)
        init {
            itemButton.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.component_name_list_button_layout,parent,false)

        return MyViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.itemButton.text = currentItem
    }

}