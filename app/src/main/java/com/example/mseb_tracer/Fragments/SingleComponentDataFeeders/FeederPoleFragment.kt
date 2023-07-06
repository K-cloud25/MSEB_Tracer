package com.example.mseb_tracer.Fragments.SingleComponentDataFeeders

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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

class FeederPoleFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_feeder_pole, container, false)

        val sendToDB : Button = view.findViewById(R.id.sendtodatabase_btn)
        sendToDB.setOnClickListener {
            val latitude = arguments?.getString("Latitude");
            val longitude = arguments?.getString("Longitude");

            val InputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_pole_height_ETV)
            val OutputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_material_ETV)
            val Efficiency_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_pole_diameter_ETV)
            val Cross_Armlen_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_cross_arm_length_ETV)
            val Cross_ArmType_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_cross_arm_type_ETV)
            val Pole_Strength_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_strength_ETV)
            val Pole_GRD_SYS_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_grounding_sys_ETV)
            val Pole_CorrRes_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_coor_res_ETV)
            val Pole_Regulation_com_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_regul_comp_ETV)
            val Pole_Lifespan_ETV = view.findViewById<TextInputEditText>(R.id.feederpole_lifespan_ETV)


            val InputVoltage_text = InputVoltage_ETV.text
            val OutputVoltage_text = OutputVoltage_ETV.text
            val Efficiency_text = Efficiency_ETV.text
            val Cross_Arnlen_text = Cross_Armlen_ETV.text
            val Cross_ArmType_text = Cross_ArmType_ETV.text
            val Pole_Strength_text =  Pole_Strength_ETV.text
            val Pole_GRD_SYS_text  = Pole_GRD_SYS_ETV.text
            val Pole_CorrRes_text = Pole_CorrRes_ETV.text
            val Pole_Regulation_com_text = Pole_Regulation_com_ETV.text
            val Pole_Lifespan_text = Pole_Lifespan_ETV.text


            val info_keys = arrayListOf(
                "Pole Height", "Pole Material",
                "Pole Diameter","Cross Arm Length",
                "Cross Arm Type","Pole Strength",
                "Grounding System", "Corrosion Resistance",
                "Regulation Compliance", "LifeSpan")
            val info = arrayListOf(
                InputVoltage_text.toString(),
                OutputVoltage_text.toString(),
                Efficiency_text.toString(),
                Cross_Arnlen_text.toString(),
                Cross_ArmType_text.toString(),
                Pole_Strength_text.toString(),
                Pole_GRD_SYS_text.toString(),
                Pole_CorrRes_text.toString(),
                Pole_Regulation_com_text.toString(),
                Pole_Lifespan_text.toString()
            )

            val apiCaller = DataCaller()
            if (longitude != null && latitude != null) {
                val response = apiCaller.callSingleComponentLoad(
                    longitude=longitude,
                    latitude=latitude,
                    name = "FeederPole",
                    info_key = info_keys,
                    information = info
                )

                if(response == "Success"){
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()

                    val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("FeederPole",
                        Context.MODE_PRIVATE
                    )

                    var unsave_items = sharedPreferences?.getInt("Unsaved_Items",0)
                    if(unsave_items == null) unsave_items = 0

                    val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                    editor!!.putInt("Unsaved_Items", unsave_items+1)
                    editor!!.putString("FeederPole"+unsave_items+1,"FeederPole")
                    editor!!.putString("longitude"+unsave_items+1,longitude)
                    editor!!.putString("latitude"+unsave_items+1,latitude)

                    val gson = Gson()
                    val info_keys_string = gson.toJson(info_keys)
                    val info_string = gson.toJson(info);

                    editor!!.putString("info_keys"+unsave_items+1,info_keys_string)
                    editor!!.putString("information"+unsave_items+1,info_string)
                    editor!!.commit()
                }

                val intent = Intent(view.context, MainActivity::class.java)
                startActivity(intent)
            }
            Log.e("KEYUR TESTING","${latitude},${longitude},${InputVoltage_text},${OutputVoltage_text},${Efficiency_text}")
        }

        return view
    }

}