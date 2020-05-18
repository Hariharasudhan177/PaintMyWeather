package com.example.paintmyweather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

public class ListViewAdaptor(private val context: Context,
                       private val dataList: MutableList<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, container: ViewGroup): View {
        var dataitem = dataList[position]
        val rowView = inflater.inflate(R.layout.list_item, null)
        rowView.findViewById<TextView>(R.id.row_name).text = dataitem;
        return rowView
    }
}