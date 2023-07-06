package com.example.mseb_tracer.Fragments.SingleComponentDataFeeders

import android.annotation.SuppressLint
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mseb_tracer.DataCaller
import com.example.mseb_tracer.MainActivity
import com.example.mseb_tracer.R
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson


class TransformerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_transformer, container, false)

        val sendToDB : Button = view.findViewById(R.id.sendtodatabase_btn)
        sendToDB.setOnClickListener {
            val latitude = arguments?.getString("Latitude");
            val longitude = arguments?.getString("Longitude");

            val InputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.transformer_inputvoltage_ETV)
            val OutputVoltage_ETV = view.findViewById<TextInputEditText>(R.id.transformer_outputvoltage_ETV)
            val Efficiency_ETV = view.findViewById<TextInputEditText>(R.id.transformer_efficiency_ETV)
            val Frequency_ETV = view.findViewById<TextInputEditText>(R.id.transformer_frequency_ETV)
            val Insulation_ETV = view.findViewById<TextInputEditText>(R.id.transformer_insulation_ETV)
            val EfficiencyR_ETV = view.findViewById<TextInputEditText>(R.id.transformer_efficiency_rel_ETV)
            val Cooling_ETV = view.findViewById<TextInputEditText>(R.id.transformer_cooling_rel_ETV)
            val Noise_ETV = view.findViewById<TextInputEditText>(R.id.transformer_noise_ETV)
            val Regulation_ETV = view.findViewById<TextInputEditText>(R.id.transformer_reg_com_ETV)
            val Lifespan_ETV = view.findViewById<TextInputEditText>(R.id.transformer_lifespan_ETV)

            val InputVoltage_text = InputVoltage_ETV.text
            val OutputVoltage_text = OutputVoltage_ETV.text
            val Efficiency_text = Efficiency_ETV.text
            val Frequency_text = Frequency_ETV.text
            val Insulation_text = Insulation_ETV.text
            val EfficiencyR_text = EfficiencyR_ETV.text
            val Cooling_text = Cooling_ETV.text
            val Noise_text = Noise_ETV.text
            val Regulation_text = Regulation_ETV.text
            val Lifespan_text = Lifespan_ETV.text

            val info_keys = arrayListOf(
                "Power Rating", "Voltage Ratio",
                "Phase configuration", "Frequency",
                "Insulation Class", "Efficiency",
                "Cooling System", "Noise Level",
                "Regulation Compliance", "Lifespan"
            )
            val info = arrayListOf(
                InputVoltage_text.toString(), OutputVoltage_text.toString(),
                Efficiency_text.toString(), Frequency_text.toString(),
                Insulation_text.toString(), EfficiencyR_text.toString(),
                Cooling_text.toString(), Noise_text.toString(),
                Regulation_text.toString(), Lifespan_text.toString()
            )

            val apiCaller = DataCaller()
            if (longitude != null && latitude != null) {
                val response = apiCaller.callSingleComponentLoad(
                    longitude=longitude,
                    latitude=latitude,
                    name = "Transformer",
                    info_key = info_keys,
                    information = info
                )

                if(response == "Success"){
                    Toast.makeText(view?.context,"Recorded Succesfully", Toast.LENGTH_SHORT).show()
                }else{
                    val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("Transfromer", MODE_PRIVATE)

                    var unsave_items = sharedPreferences?.getInt("Unsaved_Items",0)
                    if(unsave_items == null) unsave_items = 0

                    val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                    editor!!.putInt("Unsaved_Items", unsave_items+1)
                    editor!!.putString("Transformer"+unsave_items+1,"Transformer")
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