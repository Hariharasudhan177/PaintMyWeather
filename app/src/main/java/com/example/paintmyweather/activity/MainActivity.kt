package com.example.paintmyweather.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.paintmyweather.entity.CityList
import com.example.paintmyweather.custom.ListViewAdaptor
import com.example.paintmyweather.R


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

    fun ToggleVisibility(cityListCurrent: MutableList<String>) {
        val welcomMessage = findViewById<TextView>(R.id.welcomeMessage)
        val cityListView = findViewById<ListView>(R.id.list_view)
        if (cityListCurrent.size == 0) {
            welcomMessage.visibility = View.VISIBLE
            cityListView.visibility = View.GONE
        } else {
            welcomMessage.visibility = View.GONE
            cityListView.visibility = View.VISIBLE

            cityListView.adapter =
                ListViewAdaptor(
                    this@MainActivity,
                    cityListCurrent
                )
        }

    }

    override fun onResume() {
        super.onResume()
        checkCityList()
    }

    override fun onRestart() {
        super.onRestart()
        checkCityList()
    }

    public fun checkCityList() {
        val welcomMessage = findViewById<TextView>(R.id.welcomeMessage)
        val cityListView = findViewById<ListView>(R.id.list_view)

        cityList =
            CityList.checkCityListFile(
                applicationContext,
                welcomMessage,
                cityListView,
                this
            );
    }

}
