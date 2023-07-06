package com.example.mseb_tracer

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mseb_tracer.Fragments.SelectComponentFragment
import com.example.mseb_tracer.databinding.ActivityMainBinding
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException


class MainActivity : AppCompatActivity() {

    lateinit private var binding : ActivityMainBinding
    private val BASE_URL_SERVER = "http://192.168.1.2:5000";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Support Action Bar Disabled
        supportActionBar?.hide()

        //Fragment Initial
        val fragment = SelectComponentFragment()
        fragmentReplace(fragment)

    }

    private fun fragmentReplace(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(binding.mainActivityFrameLayout.id,fragment)
        fragmentTransaction.commit()

    }

}