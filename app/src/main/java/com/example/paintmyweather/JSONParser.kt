package com.example.paintmyweather

import org.json.JSONException
import org.json.JSONObject

public class JSONParser{
    companion object Factory {
        @Throws(JSONException::class)
        public fun getWeather(data: String):Temperature  {
            var temp: Temperature = Temperature();
            var jobj: JSONObject = JSONObject(data);

            val mainObj = getObject("main", jobj);
            temp.temp = getFloat("temp", mainObj);
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
    }
}