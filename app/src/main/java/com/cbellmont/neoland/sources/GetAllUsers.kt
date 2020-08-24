package com.cbellmont.neoland.sources

import android.util.Log
import com.cbellmont.neoland.MainActivityViewModel
import com.cbellmont.neoland.datamodel.user.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class GetAllUsers {
    companion object {
        private const val URL = "https://randomuser.me/api/?results=10"

        fun send(viewModel: MainActivityViewModel) {
            val client = OkHttpClient()
            val request = Request.Builder().url(URL).build()
            val call = client.newCall(request)
            viewModel.downloadStarted()
            call.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Log.e(GetAllUsers::class.simpleName, call.toString())
                    viewModel.downloadCancelled()
                }

                override fun onResponse(call: Call, response: Response) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val bodyInString = response.body?.string()
                        bodyInString?.let {
                            Log.w(GetAllUsers::class.simpleName, bodyInString)
                            val JsonObject = JSONObject(bodyInString)

                            val results = JsonObject.optJSONArray("results")
                            results?.let {
                                Log.w(GetAllUsers::class.simpleName, results.toString())
                                val gson = Gson()

                                val itemType = object : TypeToken<List<User>>() {}.type

                                val list = gson.fromJson<List<User>>(results.toString(), itemType)

                                list.forEach {
                                    Log.w(GetAllUsers::class.simpleName, it.email + it.name )
                                }
                                viewModel.downloadFinished(list)

                            }
                        }
                    }
                }
            })
        }
    }
}