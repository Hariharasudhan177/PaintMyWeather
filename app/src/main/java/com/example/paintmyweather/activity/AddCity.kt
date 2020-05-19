package com.example.paintmyweather.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.paintmyweather.R
import com.example.paintmyweather.utils.WeatherRequest
import com.example.paintmyweather.utils.WeatherSingleton

class AddCity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        var searchField = findViewById<EditText>(R.id.searchField);

        var addCity = findViewById<Button>(R.id.addbutton)
        var cityName: String = ""
        addCity.setOnClickListener() { view ->
            cityName = searchField.text.toString();
            val queue = WeatherSingleton.getInstance(
                applicationContext
            ).requestQueue
            var weatherRequest =
                WeatherRequest(applicationContext, cityName)
            var cityAddRequest = weatherRequest.cityAddRequest(searchField)
            queue.add(cityAddRequest)
        }

        var cancel = findViewById<Button>(R.id.cancelButton)
        cancel.setOnClickListener() { view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
