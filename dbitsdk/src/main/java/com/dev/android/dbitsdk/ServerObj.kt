package com.dev.android.dbitsdk

import android.content.Intent
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

        fun checkServer(userAgent: String,serverCallback: ServerCheckCallback){
            MainRetrofitInstance.userAgent = userAgent
            val callback = MainRetrofitInstance.api.serverStatus()

            callback.enqueue(object : Callback<ServerStatus> {
                override fun onResponse(call: Call<ServerStatus>, response: Response<ServerStatus>) {
                    val responseFromAPI: ServerStatus? = response.body()

                    Log.e("response", "" + responseFromAPI)

                    if (response.isSuccessful && (responseFromAPI != null)) {

                        val status = responseFromAPI.status
                        if(status!="Error") {
                            serverCallback.onResult(true)
                        }else{
                            serverCallback.onResult(false)
                        }
                    }else{
                        serverCallback.onResult(false)
                    }
                }

                override fun onFailure(call: Call<ServerStatus>, t: Throwable) {
                    serverCallback.onResult(false)
                }
            })
        }