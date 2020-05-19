package com.example.paintmyweather.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.paintmyweather.R
import com.example.paintmyweather.utils.WeatherRequest
import com.example.paintmyweather.utils.WeatherSingleton

public class ListViewAdaptor(
    private val context: Context,
    private val dataList: MutableList<String>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): String {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
        var dataitem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_item, null)
        rowView.findViewById<TextView>(R.id.row_name).text = dataitem.capitalize();
        //rowView.findViewById<TextView>(R.id.row_temp).text = dataitem[1];

        val queue = WeatherSingleton.getInstance(context).requestQueue
        var weatherRequest = WeatherRequest(context, dataitem)
        var cityAddRequest = weatherRequest.tempRequest(
            rowView.findViewById<TextView>(R.id.row_temp),
            rowView.findViewById<TextView>(R.id.row_detail),
            rowView.findViewById<ImageView>(R.id.icon)
        )
        queue.add(cityAddRequest)



        return rowView
    }
}