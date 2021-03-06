package com.example.paintmyweather.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.example.paintmyweather.entity.CityList
import com.example.paintmyweather.entity.Temperature
import com.squareup.picasso.Picasso
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.xmlpull.v1.XmlPullParserException
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.SocketException
import java.net.SocketTimeoutException


public class WeatherRequest(
    private val context: Context,
    private val cityName: String
) {
    val url = "https://api.openweathermap.org/data/2.5/weather?q=" +
            cityName +
            "&appid=f0b529b1bc5787191a9ad7f1db867181"

    val urlImage = "https://openweathermap.org/img/w/";

    var temp: Temperature =
        Temperature();
    var tempInCelcius: String = ""

    public fun cityAddRequest(editText: EditText): JsonObjectRequest {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                CityList.addToCityList(cityName.toLowerCase(), context)
                editText.setText("")
                Toast.makeText(context, "$cityName added", Toast.LENGTH_LONG).show()
            },
            //Response.ErrorListener { error -> textView.text = error.toString() })
            Response.ErrorListener {

                    error ->
                Toast.makeText(context, getVolleyError(error, context), Toast.LENGTH_LONG).show()
            })

        return weatherRequest;
    }

    public fun tempRequest(
        tempView: TextView,
        detailView: TextView,
        imageView: ImageView
    ): JsonObjectRequest {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                var data: String = response.toString()
                temp = JSONParser.getWeather(data)
                tempInCelcius += Math.round((temp.temp - 273.15))
                tempView.text = tempInCelcius + "\u2103"
                detailView.text = temp.detail
                var iconName = temp.icon

                val path = urlImage + iconName + ".png"
                Picasso.get()
                    .load(path)
                    .into(imageView);

            },
            //Response.ErrorListener { error -> textView.text = error.toString() })
            Response.ErrorListener {

                    error ->
                Toast.makeText(context, getVolleyError(error, context), Toast.LENGTH_LONG).show()
            })

        return weatherRequest;
    }

    public fun detailRequest(
        tempView: TextView,
        detailView: TextView,
        imageView: ImageView,
        humidity: TextView
    ): JsonObjectRequest {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->

                var data: String = response.toString()
                temp = JSONParser.getWeather(data)
                tempInCelcius += Math.round((temp.temp - 273.15))
                tempView.text = tempInCelcius + "\u2103"
                detailView.text = temp.detail
                var iconName = temp.icon

                Picasso.get()
                    .load(urlImage + iconName + ".png")
                    .into(imageView);
                humidity.text = "Humidity = " + temp.humidity.toString()
            },
            //Response.ErrorListener { error -> textView.text = error.toString() })
            Response.ErrorListener {

                    error ->
                Toast.makeText(context, getVolleyError(error, context), Toast.LENGTH_LONG).show()
            })

        return weatherRequest;
    }

    fun getVolleyError(error: VolleyError, context: Context): String {
        var errorMsg = ""
        if (error is NoConnectionError) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var activeNetwork: NetworkInfo? = null
            activeNetwork = cm.activeNetworkInfo
            errorMsg = if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
                "Server is not connected to the internet. Please try again"
            } else {
                "Your device is not connected to internet.please try again with active internet connection"
            }
        } else if (error is NetworkError || error.cause is ConnectException) {
            errorMsg =
                "Your device is not connected to internet.please try again with active internet connection"
        } else if (error.cause is MalformedURLException) {
            errorMsg = "That was a bad request please try again…"
        } else if (error is ParseError || error.cause is IllegalStateException || error.cause is JSONException || error.cause is XmlPullParserException) {
            errorMsg = "There was an error parsing data…"
        } else if (error.cause is OutOfMemoryError) {
            errorMsg = "Device out of memory"
        } else if (error is AuthFailureError) {
            errorMsg = "Failed to authenticate user at the server, please contact support"
        } else if (error is ServerError || error.cause is ServerError) {
            val networkResponse = error.networkResponse
            val statusCode = networkResponse?.statusCode ?: 0
            if (statusCode == 404 || statusCode == 400) {
                errorMsg = "City Name Not Found"
            } else {
                errorMsg = "Internal server error occurred please try again...."
            }

        } else if (error is TimeoutError || error.cause is SocketTimeoutException || error.cause is ConnectTimeoutException || error.cause is SocketException || (error.cause!!.message != null && error.cause!!.message!!.contains(
                "Your connection has timed out, please try again"
            ))
        ) {
            errorMsg = "Your connection has timed out, please try again"
        } else {
            errorMsg = "An unknown error occurred during the operation, please try again"
        }
        return errorMsg
    }

}