package com.example.mseb_tracer.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mseb_tracer.Fragments.MultiComponentDataFeeders.HT_Line_Fragment
import com.example.mseb_tracer.Fragments.MultiComponentDataFeeders.Transmission_Line_Fragment
import com.example.mseb_tracer.Fragments.SingleComponentDataFeeders.TransformerFragment
import com.example.mseb_tracer.R
import com.example.mseb_tracer.adapters.MultiGeoList_Adapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class Multi_Component_Data_Fragment : Fragment() {

    lateinit var arrLat : ArrayList<String>
    lateinit var arrLong : ArrayList<String>

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var GeoList_RecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arrLat = arrayListOf()
        arrLong = arrayListOf()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_multi__component__data_, container, false)

        fusedLocationProviderClient = context?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        val title_TV : TextView = view.findViewById(R.id.multi_component_name_TV)
        val componentName = requireArguments().getString("ComponentName")
        title_TV.text = componentName

        GeoList_RecyclerView = view.findViewById(R.id.multi_geo_point_RV)
        //SetupRVCall
        setupRV(view)

        val geoMarkBtn : Button = view.findViewById(R.id.multi_component_getLatLongbtn)
        val dataFragmentCaller : Button = view.findViewById(R.id.multi_open_data_entering_field_btn)

        geoMarkBtn.setOnClickListener {
            alsoFetchLocation(view)
        }

        dataFragmentCaller.setOnClickListener {
            if(arrLat.size != 0 && arrLong.size != 0){
                when(componentName){

                    "H.T.Line" -> {
                        val fragment = HT_Line_Fragment()
                        val bundle: Bundle = Bundle()
                        bundle.putStringArrayList("Latitude",arrLat)
                        bundle.putStringArrayList("Longitude",arrLong)
                        fragment.arguments = bundle
                        replaceFragment(fragment, view)
                    }
                    "Transmission Line"->{
                        val fragment = Transmission_Line_Fragment()
                        val bundle: Bundle = Bundle()
                        bundle.putStringArrayList("Latitude",arrLat)
                        bundle.putStringArrayList("Longitude",arrLong)
                        fragment.arguments = bundle
                        replaceFragment(fragment, view)
                    }
                }
            }
        }

        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupRV(view : View){
        val adpater = MultiGeoList_Adapter(arrLat,arrLong)
        GeoList_RecyclerView.layoutManager = LinearLayoutManager(view.context)
        GeoList_RecyclerView.setHasFixedSize(true)
        GeoList_RecyclerView.adapter = adpater
        adpater.notifyDataSetChanged()
        GeoList_RecyclerView.scrollToPosition(arrLat.size - 1)
    }

    private fun replaceFragment(fragment: Fragment,view : View){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(view.findViewById<FrameLayout>(R.id.single_component_information_wrapper_Frame).id,fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun alsoFetchLocation(view : View) : ArrayList<String>{
        var lat = ""
        var lon = ""
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            } == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                override fun isCancellationRequested() = false
            })
                .addOnSuccessListener { location: Location? ->
                    if (location == null)
                        Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                    else {
                        lat = location.latitude.toString();
                        lon = location.longitude.toString()
                        view?.findViewById<TextView>(R.id.single_component_latitude_TV)?.text = "Latitude : " + lat
                        view?.findViewById<TextView>(R.id.single_component_longitude_TV)?.text = "Longitude : " + lon
                        arrLat.add(lat); arrLong.add(lon)
                        setupRV(view)
                    }

                }.addOnFailureListener {
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                }
        }
        return arrayListOf<String>(lat,lon)
    }
}