package com.example.paintmyweather

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import org.xmlpull.v1.XmlPullParserException
import java.net.ConnectException
import java.net.MalformedURLException
import java.net.SocketException
import java.net.SocketTimeoutException
import com.android.volley.NetworkResponse


public class WeatherRequest(private val context: Context,
                            private val cityName: String){
    val url = "https://api.openweathermap.org/data/2.5/weather?q=" +
            cityName +
            "&appid=f0b529b1bc5787191a9ad7f1db867181"

    var temp:Temperature = Temperature();
    var tempInCelcius: String = ""

    public fun cityAddRequest():JsonObjectRequest  {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->
                CityList.addToCityList(cityName, context)
                Toast.makeText(context, "$cityName added", Toast.LENGTH_LONG).show()
            },
            //Response.ErrorListener { error -> textView.text = error.toString() })
            Response.ErrorListener {

                    error ->
                Toast.makeText(context, getVolleyError(error, context), Toast.LENGTH_LONG).show()
            })

        return weatherRequest;
    }

    public fun tempRequest(textView: TextView):JsonObjectRequest  {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->

                var data:String = response.toString()
                temp =JSONParser.getWeather(data)
                tempInCelcius+=Math.round((temp.temp - 273.15))
                textView.text = tempInCelcius

            },
            //Response.ErrorListener { error -> textView.text = error.toString() })
            Response.ErrorListener {

                    error ->
                Toast.makeText(context, getVolleyError(error, context), Toast.LENGTH_LONG).show()
            })

        return weatherRequest;
    }

    public fun detailRequest(textView: TextView):JsonObjectRequest  {
        val weatherRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->

                var data:String = response.toString()
                temp =JSONParser.getWeather(data)
                tempInCelcius+=Math.round((temp.temp - 273.15))
                textView.text = tempInCelcius
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
            errorMsg = "Your device is not connected to internet.please try again with active internet connection"
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
            if(statusCode == 404){
                errorMsg = "City Name Not Found"
            }else {
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