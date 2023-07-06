package com.example.mseb_tracer.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.mseb_tracer.Fragments.SingleComponentDataFeeders.FeederPoleFragment
import com.example.mseb_tracer.Fragments.SingleComponentDataFeeders.FeedersFragment
import com.example.mseb_tracer.Fragments.SingleComponentDataFeeders.TransformerFragment
import com.example.mseb_tracer.R
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.w3c.dom.Text

class Single_Component_Data_Fragment : Fragment() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    public var longitudeParent : String = ""
    public var latitudeParent : String =  ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single__component__data_, container, false)


        fusedLocationProviderClient = context?.let {
            LocationServices.getFusedLocationProviderClient(
                it
            )
        }!!

        val title_componentName = view.findViewById<TextView>(R.id.single_component_name_TV)
        val ComponentName = requireArguments().getString("ComponentName")
        title_componentName.text = ComponentName

        val buttonClick = view.findViewById<Button>(R.id.single_component_getLatLongbtn)
        val latitudeTextView = view.findViewById<TextView>(R.id.single_component_latitude_TV)
        val longitudeTextView = view.findViewById<TextView>(R.id.single_component_longitude_TV)


        buttonClick.setOnClickListener {
            alsoFetchLocation()
        }

        val fragmentOpener = view.findViewById<Button>(R.id.open_data_entering_field_btn)
        fragmentOpener.setOnClickListener {
            if(latitudeParent.length == 0 && longitudeParent.length == 0){
                Toast.makeText(context,"Mark Geo_Coordinate",Toast.LENGTH_SHORT).show()
            }else {
                when (ComponentName) {

                    "Transformer" -> {
                        val fragment = TransformerFragment()
                        val bundle: Bundle = Bundle()
                        bundle.putString("Latitude", latitudeParent)
                        bundle.putString("Longitude", longitudeParent)
                        fragment.arguments = bundle
                        replaceFragment(fragment, view)
                    }

                    "Feeder" -> {
                        val fragment = FeedersFragment()
                        val bundle : Bundle = Bundle()
                        bundle.putString("Latitude",latitudeParent)
                        bundle.putString("Longitude",longitudeParent)
                        fragment.arguments = bundle
                        replaceFragment(fragment,view)
                    }

                    "FeederPole" ->{
                        val fragment = FeederPoleFragment()
                        val bundle : Bundle = Bundle()
                        bundle.putString("Latitude",latitudeParent)
                        bundle.putString("Longitude",longitudeParent)
                        fragment.arguments = bundle
                        replaceFragment(fragment,view)
                    }
                }
            }
        }

        return view;
    }

    private fun replaceFragment(fragment: Fragment,view : View){
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(view.findViewById<FrameLayout>(R.id.single_component_information_wrapper_Frame).id,fragment)
        fragmentTransaction.commit()
    }

    @SuppressLint("SetTextI18n")
    private fun alsoFetchLocation() : ArrayList<String>{
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
                        latitudeParent = lat; longitudeParent = lon
                    }

                }.addOnFailureListener {
                    Toast.makeText(context, "Cannot get location.", Toast.LENGTH_SHORT).show()
                }
        }
        return arrayListOf<String>(lat,lon)
    }


}