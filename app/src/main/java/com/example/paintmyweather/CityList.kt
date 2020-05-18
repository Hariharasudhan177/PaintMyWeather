package com.example.paintmyweather

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

public class CityList{
    companion object cityList {

        var cityList: MutableList<String> = mutableListOf()

        fun checkCityListFile(context: Context,
                              welcomeMessage: TextView,
                              cityListView: ListView,
                              activity: MainActivity): MutableList<String>{

            var cityListFromFile = mutableListOf<String>()
            cityListFromFile = getCityListFromFile(context)
            this.ToggleVisibility(cityListFromFile,welcomeMessage,cityListView,context, activity)
            return cityListFromFile

        }

        fun getCityListFromFile(context: Context): MutableList<String>{

            val cityListFromFile = mutableListOf<String>()
            val cityListFileName = "cityListFileName"
            var fileInputStream: FileInputStream? = null

            try{

                fileInputStream = context.openFileInput(cityListFileName)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                var text: String? = ""
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    val city  = text
                    if(city != null){
                        cityListFromFile.add(city);
                    }
                }

                return cityListFromFile

            }catch(e: Exception){
                e.printStackTrace()
            }

            return mutableListOf()
        }

        fun addToCityList(newCity:String, context: Context){
            cityList = CityList.getCityListFromFile(context);
            val cityListFileName = "cityListFileName"
            context.openFileOutput(cityListFileName, Context.MODE_PRIVATE).use { cityFile ->

                var cityPresentAlready = false;

                if(cityList.size != 0){
                    cityList.forEach {city ->
                        if(city == newCity) {
                            cityPresentAlready = true
                        }
                        cityFile.write(city.toByteArray())
                        cityFile.write(("\n").toByteArray())
                    }
                }

                if(!cityPresentAlready) {
                    cityFile.write(newCity.toByteArray())
                    cityFile.write(("\n").toByteArray())
                    cityList.add(newCity)
                }
            }
        }

        fun removeCityFromList(removeCity:String?,
                               context: Context){
            val cityListFileName = "cityListFileName"
            context.openFileOutput(cityListFileName, Context.MODE_PRIVATE).use { cityFile ->

                Log.d("hari", "haril"+cityList.size)
                var indexToDelete = -1;
                if(cityList.size != 0){
                    Log.d("hari", "harik"+cityList.size)
                    cityList.forEachIndexed() {index,city ->
                        Log.d("hari", "harik"+city+removeCity)
                        if(city != removeCity) {
                            cityFile.write(city.toByteArray())
                            cityFile.write(("\n").toByteArray())

                        }else {
                            indexToDelete = index
                        }
                    }
                    cityList.removeAt(indexToDelete)
                }
            }
        }

        fun ToggleVisibility(cityListCurrent: MutableList<String>,
                             welcomeMessage: TextView,
                             cityListView:ListView,
                             context: Context,
                             activity: MainActivity){
            if(cityListCurrent.size == 0){
                welcomeMessage.visibility = View.VISIBLE
                cityListView.visibility = View.GONE
            }else {
                welcomeMessage.visibility = View.GONE
                cityListView.visibility = View.VISIBLE

                cityListView.adapter = ListViewAdaptor(context, cityListCurrent)

                cityListView.setOnItemClickListener{ parent, view, position, id ->
                    var intent = Intent(context, Detail::class.java)
                    intent.flags=FLAG_ACTIVITY_NEW_TASK;
                    var cityName:String = parent.getItemAtPosition(position) as String
                    intent.putExtra("position", cityName)
                    context.startActivity(intent)
                }

                cityListView.setOnItemLongClickListener { parent, view, position, id ->
                    var supportFragmentManager = activity.supportFragmentManager;
                    var removeD = RemoveDialog()
                    var cityName:String = parent.getItemAtPosition(position) as String
                    removeD.show(supportFragmentManager, cityName)
                    true
                }
            }
        }
    }
}