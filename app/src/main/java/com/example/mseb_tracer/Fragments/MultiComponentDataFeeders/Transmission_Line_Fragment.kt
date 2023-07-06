package com.example.mseb_tracer.Fragments.MultiComponentDataFeeders

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.mseb_tracer.DataCaller
import com.example.mseb_tracer.MainActivity
import com.example.mseb_tracer.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson

class Transmission_Line_Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_transmission__line_, container, false)

        val thickness_editTextView : TextInputEditText = view.findViewById(R.id.transmission_thickness_ETV)
        val material_type_editTextView : TextInputEditText = view.findViewById(R.id.transmission_material_ETV)

        val sendToDB_Btn : Button = view.findViewById(R.id.sendtodatabase_btn)
        sendToDB_Btn.setOnClickListener {

            val info_keys = arrayListOf("Thickness_of_Wire","Material_Type")
            val information = arrayListOf(thickness_editTextView.text.toString(),material_type_editTextView.text.toString())
            val latitude = arguments?.getStringArrayList("Latitude")
            val longitude = arguments?.getStringArrayList("Longitude")

            val apiCaller = DataCaller()
            if (longitude != null && latitude != null) {
                val response = apiCaller.callMultiComponentLoad(
                    longitude=longitude,
                    latitude=latitude,
                    name = "TransmissionLine",
                    info_keys = info_keys,
                    info = information
                )

                if(response == "Success"){
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(view?.context,"Recorded Unsuccesfully", Toast.LENGTH_SHORT).show()

                    val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("Transmission",
                        Context.MODE_PRIVATE
                    )

                    var unsave_items = sharedPreferences?.getInt("Unsaved_Items",0)
                    if(unsave_items == null) unsave_items = 0

                    val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                    editor!!.putInt("Unsaved_Items", unsave_items+1)
                    editor!!.putString("TransmissionLine"+unsave_items+1,"TransmissionLine")

                    val gson = Gson()
                    val info_keys_string = gson.toJson(info_keys)
                    val info_string = gson.toJson(information);
                    val latitude_stirng = gson.toJson(latitude);
                    val longitude_string = gson.toJson(longitude);

                    editor!!.putString("longitude"+unsave_items+1,longitude_string)
                    editor!!.putString("latitude"+unsave_items+1,latitude_stirng)
                    editor!!.putString("info_keys"+unsave_items+1,info_keys_string)
                    editor!!.putString("information"+unsave_items+1,info_string)
                    editor!!.commit()
                }

                val intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
    }
        return view
    }
}