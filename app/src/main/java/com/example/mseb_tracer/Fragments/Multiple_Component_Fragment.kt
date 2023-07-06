package com.example.mseb_tracer.Fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mseb_tracer.R
import com.example.mseb_tracer.activities.RecordingActivity
import com.example.mseb_tracer.adapters.ComponentItemAdapter
import java.util.*
import kotlin.collections.ArrayList


class Multiple_Component_Fragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_multiple__component_, container, false)

        val multi_list =  ArrayList<String>(Arrays.asList(*resources.getStringArray(R.array.multiple_component_list)))

        val multi_list_recyclerView = view.findViewById<RecyclerView>(R.id.multiple_component_list_RV)
        val adapter = ComponentItemAdapter(multi_list)
        multi_list_recyclerView.setHasFixedSize(true)
        multi_list_recyclerView.adapter = adapter
        multi_list_recyclerView.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickedListner(object : ComponentItemAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                Toast.makeText(context,"You Clicked On ${multi_list[position]}",Toast.LENGTH_SHORT).show()
                val intent = Intent(context,RecordingActivity::class.java)
                intent.putExtra("ComponentName",multi_list[position])
                startActivity(intent)
            }
        })

        return view
    }

}