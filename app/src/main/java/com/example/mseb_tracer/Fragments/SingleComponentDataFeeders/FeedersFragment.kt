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

class FeedersFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feeders, container, false)

        val sendToDB : Button = view.findViewById(R.id.sendtodatabase_btn)
        sendToDB.setOnClickListener {
            val latitude = arguments?.getString("Latitude");
            val longitude = arguments?.getString("Longitude");

            val InputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.feeder_inputvoltage_ETV)
            val OutputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.feeder_outputvoltage_ETV)
            val Efficiency_ETV = view.findViewById<TextInputEditText>(R.id.feeder_efficiency_ETV)
            val Insulation_ETV = view.findViewById<TextInputEditText>(R.id.feeder_insulaton_ETV )
            val SpanLength_ETV = view.findViewById<TextInputEditText>(R.id.feeder_span_len_ETV)
            val SupportType_ETV = view.findViewById<TextInputEditText>(R.id.feeder_support_type_ETV)
            val SupportHeight_ETV = view.findViewById<TextInputEditText>(R.id.feeder_support_height_ETV)
            val LoadCap_ETV = view.findViewById<TextInputEditText>(R.id.feeder_load_cap_ETV)
            val FaultTol_ETV = view.findViewById<TextInputEditText>(R.id.feeder_fault_tol_ETV)
            val RegCom_ETV = view.findViewById<TextInputEditText>(R.id.feeder_reg_com_ETV)

            val InputVoltage_text = InputVoltage_ETV.text
            val OutputVoltage_text = OutputVoltage_ETV.text
            val Efficiency_text = Efficiency_ETV.text
            val Insulation_text = Insulation_ETV.text
            val SpanLength_text = SpanLength_ETV.text
            val SupportType_text = SupportType_ETV.text
            val SupportHeight_text = SupportHeight_ETV.text
            val LoadCap_text = LoadCap_ETV.text
            val FaultTol_text = FaultTol_ETV.text
            val RegCom_text = RegCom_ETV.text

            val info_keys = arrayListOf(
                "Conductor Size","Conductor Material","Conductor Configuration",
                "Conductor Insulation", "Span Length","Support Struture Type","Support Height",
                "Load Capacity", "Fault Tolerance","Regulation Compliance"
            )
            val info = arrayListOf(
                InputVoltage_text.toString(),OutputVoltage_text.toString(),Efficiency_text.toString(),
                Insulation_text.toString(),SpanLength_text.toString(),SupportType_text.toString(),
                SupportHeight_text.toString(),LoadCap_text.toString(),FaultTol_text.toString(),
                RegCom_text.toString()
            )

            val apiCaller = DataCaller()
            if (longitude != null && latitude != null) {
                val response = apiCaller.callSingleComponentLoad(
                    longitude=longitude,
                    latitude=latitude,
                    name = "Feeder",
                    info_key = info_keys,
                    information = info
                )

                if(response == "Success"){
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()

                    val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("Feeders",
                        Context.MODE_PRIVATE
                    )

                    var unsave_items = sharedPreferences?.getInt("Unsaved_Items",0)
                    if(unsave_items == null) unsave_items = 0

                    val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                    editor!!.putInt("Unsaved_Items", unsave_items+1)
                    editor!!.putString("Feegers"+unsave_items+1,"Feeders")
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