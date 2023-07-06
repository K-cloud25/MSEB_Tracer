package com.example.mseb_tracer.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.mseb_tracer.Fragments.Multi_Component_Data_Fragment
import com.example.mseb_tracer.Fragments.Single_Component_Data_Fragment
import com.example.mseb_tracer.R
import com.example.mseb_tracer.databinding.ActivityRecordingBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener


class RecordingActivity : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val Location_PERMISSION_REQUEST_CODE = 101

    lateinit var binding : ActivityRecordingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordingBinding.inflate(layoutInflater)
        val view : View = binding.root
        setContentView(view)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                Location_PERMISSION_REQUEST_CODE
            )
        }

        val type = intent.getStringExtra("ComponentType")
        val name = intent.getStringExtra("ComponentName")

        if(type == "Single"){
            val fragment = Single_Component_Data_Fragment()
            val bundle = Bundle()
            bundle.putString("ComponentName",name)
            fragment.arguments = bundle
            fragmentReplace(fragment)
        }else{
            val fragment = Multi_Component_Data_Fragment()
            val bundle = Bundle()
            bundle.putString("ComponentName",name)
            fragment.arguments = bundle
            fragmentReplace(fragment)
        }


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@RecordingActivity)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Location_PERMISSION_REQUEST_CODE -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(this@RecordingActivity,"Permissions Granted",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun fragmentReplace(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(binding.recordingCastingFrameLayout.id,fragment)
        fragmentTransaction.commit()

    }
}