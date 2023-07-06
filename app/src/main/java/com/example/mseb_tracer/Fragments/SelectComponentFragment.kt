package com.example.mseb_tracer.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mseb_tracer.R

class SelectComponentFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view =  inflater.inflate(R.layout.fragment_select_component, container, false)

        val Single_Components_Btn = view.findViewById<Button>(R.id.single_component_btn)
        val Multiple_Component_Btn = view.findViewById<Button>(R.id.multiple_component_btn)
        val Reload_Data_btn = view.findViewById<Button>(R.id.reload_data_btn)

        Single_Components_Btn.setOnClickListener {
            val singleFragment = Single_Components_Fragment()
            fragmentReplace(singleFragment)
        }

        Multiple_Component_Btn.setOnClickListener {
            val multiFragment = Multiple_Component_Fragment()
            fragmentReplace(multiFragment)
        }

        return view
    }

    private fun fragmentReplace(fragment : Fragment){
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity_frame_layout,fragment)
        fragmentTransaction.commit()
        fragmentTransaction.addToBackStack(null)
    }

}