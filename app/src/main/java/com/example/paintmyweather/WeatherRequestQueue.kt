package com.example.paintmyweather

import android.content.Context
import org.json.JSONObject

public class WeatherRequestQueue(private val context: Context,
                            private val cityList:MutableList<String>){
    val queue = WeatherSingleton.getInstance(context).requestQueue

    public fun addAllCityRequest()  {
        cityList.forEach { city->
            var weatherRequest=WeatherRequest(context, city)
            //var request = weatherRequest.tempRequest()
            //queue.add(request)
        }
    }

}