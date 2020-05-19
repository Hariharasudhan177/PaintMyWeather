package com.example.paintmyweather.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.paintmyweather.R
import com.example.paintmyweather.utils.WeatherRequest
import com.example.paintmyweather.utils.WeatherSingleton

class Detail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val cityName: String? = intent.getStringExtra("position")

        if (cityName != null) {
            findViewById<TextView>(R.id.row_name).text = cityName.capitalize()
            val queue = WeatherSingleton.getInstance(
                applicationContext
            ).requestQueue
            var weatherRequest =
                WeatherRequest(applicationContext, cityName)
            var cityAddRequest = weatherRequest.detailRequest(
                findViewById<TextView>(R.id.row_temp),
                findViewById<TextView>(R.id.row_detail),
                findViewById<ImageView>(R.id.icon),
                findViewById<TextView>(R.id.row_humidity)
            )
            queue.add(cityAddRequest)
        }
    }
}
