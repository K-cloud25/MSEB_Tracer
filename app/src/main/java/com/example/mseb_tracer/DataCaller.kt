package com.example.mseb_tracer

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class DataCaller {

    private val BASE_URL : String = "https://elaboratefloralwhiteability.msebproject.repl.co";

    public fun callSingleComponentLoad(
        longitude : String,
        latitude : String,
        info_key : ArrayList<String>?,
        information : ArrayList<String>?,
        name : String
    ):String {
        val client = OkHttpClient().newBuilder().build()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(contentType = mediaType, "{ " +
                "\"name\" : \""+name+"\" ," +
                "\"latitude\" : \""+ latitude + "\"," +
                "\"longitude\" : \""+ longitude + "\"," +
                "\"info_key\" : \"" + info_key + "\"," +
                "\"information\" : \""+ information + "\"}"
        )

        val request = Request.Builder()
            .url(BASE_URL+"/single_components")
            .method("POST",body)
            .addHeader("Content-Type","application/json")
            .build()

        var returnMessage= ""

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                returnMessage = "Success"
                Log.e("KEYUR TESTING",e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                returnMessage = "Failure"
                Log.e("KEYUR TESTING",response.body?.string().toString())
            }

        })

        if(returnMessage == "Success"){
            return returnMessage
        }else{
            return ""
        }
    }

    public fun callMultiComponentLoad(
        latitude : ArrayList<String>,
        longitude : ArrayList<String>,
        info_keys : ArrayList<String>,
        info : ArrayList<String>,
        name : String
    ): String {
        val client = OkHttpClient().newBuilder().build()
        val mediaType = "application/json".toMediaTypeOrNull()
        val body = RequestBody.create(contentType = mediaType, "{ " +
                "\"name\" : \""+name+"\" ," +
                "\"latitude\" : \""+ latitude + "\"," +
                "\"longitude\" : \""+ longitude + "\"," +
                "\"info_key\" : \"" + info_keys + "\"," +
                "\"information\" : \""+ info + "\"}"
        )

        val request = Request.Builder()
            .url(BASE_URL+"/post_body")
            .method("POST",body)
            .addHeader("Content-Type","application/json")
            .build()

        var returnMessage= ""

        client.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                returnMessage = "Success"
                Log.e("KEYUR TESTING",e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                returnMessage = "Failure"
                Log.e("KEYUR TESTING",response.body?.string().toString())
            }

        })

        if(returnMessage == "Success"){
            return returnMessage
        }else{
            return ""
        }
    }
}