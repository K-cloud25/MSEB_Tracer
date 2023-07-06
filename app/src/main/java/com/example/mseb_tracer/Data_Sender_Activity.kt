package com.example.mseb_tracer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class Data_Sender_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sender)
    }

    fun run() {


        val client2 = OkHttpClient().newBuilder().build()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(contentType = mediaType,"{\"name\" : \""+ arrayListOf("Keyur","Mathur","Landge")  +"\"}")
        val request2 = Request.Builder()
            .url("http://10.2.35.143:5000/post_body")
            .method("POST",body)
            .addHeader("Content-Type","application/json")
            .build()

        client2.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Keyur Error" , e.message.toString());
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body!!.string()
                Log.e("Keyur Access", responseBody);
            }

        })


    }
}