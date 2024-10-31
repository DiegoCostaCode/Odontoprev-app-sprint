package com.example.odontoprev_app.utils.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.odontoprev_app.BuildConfig
import org.json.JSONObject

class RemoteApi(private val context: Context) {

    private val url = BuildConfig.apiKey
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    fun post(path: String, jsonBody: JSONObject, onSuccess: (JSONObject) -> Unit, onError: (String) -> Unit) {

        var urlPath :String = "$url$path";

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST,
            urlPath,
            jsonBody,
            { response ->
                onSuccess(response)
            },
            { error ->
                onError(error.message ?: "Erro desconhecido")
                Log.e("ApiClient", "Erro: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }
}
