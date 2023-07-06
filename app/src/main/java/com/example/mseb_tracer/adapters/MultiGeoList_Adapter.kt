package com.example.mseb_tracer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mseb_tracer.R

class MultiGeoList_Adapter(protected val latList : ArrayList<String>, protected val longList : ArrayList<String>)
    :RecyclerView.Adapter<MultiGeoList_Adapter.MyViewHolder>(){

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val latitude_TV : TextView = itemView.findViewById(R.id.multi_card_single_geo_lat)
        val longitude_TV : TextView = itemView.findViewById(R.id.multi_card_single_geo_long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.geopoint_list_multi_component_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return latList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentLat = latList[position]
        val currentLong = longList[position]

        holder.latitude_TV.text = "Latitude : "+ currentLat
        holder.longitude_TV.text = "Longitude : " + currentLong
    }

}