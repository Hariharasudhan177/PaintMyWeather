package com.example.paintmyweather.utils

import com.example.paintmyweather.entity.Temperature
import org.json.JSONException
import org.json.JSONObject

public class JSONParser {
    companion object Factory {
        @Throws(JSONException::class)
        public fun getWeather(data: String): Temperature {
            var temp: Temperature =
                Temperature();
            var jobj: JSONObject = JSONObject(data);

            val mainObj =
                getObject("main", jobj)

            val arr = jobj.getJSONArray("weather")
            val weatherObj = arr.getJSONObject(0)

            temp.temp =
                getFloat("temp", mainObj)
            temp.detail = getString(
                "description",
                weatherObj
            )
            temp.icon =
                getString("icon", weatherObj);
            temp.humidity =
                getFloat("humidity", mainObj)
            return temp;
        }

        @Throws(JSONException::class)
        private fun getObject(tagName: String, jObj: JSONObject): JSONObject {
            return jObj.getJSONObject(tagName)
        }

        @Throws(JSONException::class)
        private fun getFloat(tagName: String, jObj: JSONObject): Float {
            return jObj.getDouble(tagName).toFloat()
        }

        @Throws(JSONException::class)
        private fun getString(tagName: String, jObj: JSONObject): String {
            return jObj.getString(tagName)
        }
    }
}