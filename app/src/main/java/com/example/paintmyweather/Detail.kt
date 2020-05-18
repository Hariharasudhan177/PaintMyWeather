package com.example.paintmyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val cityName:String? = intent.getStringExtra("position")
        val textView = findViewById<TextView>(R.id.temp)

        if(cityName != null){
            val queue = WeatherSingleton.getInstance(applicationContext).requestQueue
            var weatherRequest=WeatherRequest(applicationContext, cityName)
            var cityAddRequest = weatherRequest.detailRequest(textView)
            queue.add(cityAddRequest)
        }
    }
}
