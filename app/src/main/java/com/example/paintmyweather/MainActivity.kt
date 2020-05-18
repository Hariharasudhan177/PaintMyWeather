package com.example.paintmyweather

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class MainActivity : AppCompatActivity() {

    var cityList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkCityList();

        val fab: View = findViewById(R.id.fab)

        fab.setOnClickListener { view ->
            val intent = Intent(this, AddCity::class.java)
            startActivity(intent)
        }

        var swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener {
            checkCityList()

            if (null != swipeRefreshLayout) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    fun ToggleVisibility(cityListCurrent: MutableList<String>){
        val welcomMessage = findViewById<TextView>(R.id.welcomeMessage)
        val cityListView = findViewById<ListView>(R.id.list_view)
        if(cityListCurrent.size == 0){
            welcomMessage.visibility = View.VISIBLE
            cityListView.visibility = View.GONE
        }else {
            welcomMessage.visibility = View.GONE
            cityListView.visibility = View.VISIBLE

            Log.d("hari", "hari" + cityListCurrent[0])
            cityListView.adapter = ListViewAdaptor(this@MainActivity, cityListCurrent)
        }

    }

    override fun onResume() {
        Log.d("hari", "onResume")
        super.onResume()
        checkCityList()
    }

    override fun onRestart() {
        super.onRestart()
        checkCityList()
    }

    public fun checkCityList(){
        val welcomMessage = findViewById<TextView>(R.id.welcomeMessage)
        val cityListView = findViewById<ListView>(R.id.list_view)

        cityList = CityList.checkCityListFile(applicationContext,welcomMessage,cityListView,this);
    }

}
