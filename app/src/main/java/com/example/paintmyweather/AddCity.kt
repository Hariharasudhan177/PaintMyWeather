package com.example.paintmyweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class AddCity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_city)

        var searchField = findViewById<EditText>(R.id.searchField);

        var addCity = findViewById<Button>(R.id.addbutton)
        var cityName: String = ""
        addCity.setOnClickListener(){ view ->
            cityName = searchField.text.toString();
            val queue = WeatherSingleton.getInstance(applicationContext).requestQueue
            var weatherRequest=WeatherRequest(applicationContext, cityName)
            var request = weatherRequest.tempRequest()
            queue.add(request)
        }

        var cancel = findViewById<Button>(R.id.cancelButton)
        cancel.setOnClickListener(){ view ->
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
