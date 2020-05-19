package com.example.paintmyweather.custom

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.paintmyweather.activity.MainActivity
import com.example.paintmyweather.entity.CityList


class RemoveDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val activityMain = activity!!;
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Remove City")
                .setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialog, id ->
                        CityList.removeCityFromList(tag, activityMain.applicationContext);
                        (activityMain as MainActivity).checkCityList();
                    })
                .setNegativeButton("No",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onDetach() {
        super.onDetach()

        try {
            val childFragmentManager =
                Fragment::class.java!!.getDeclaredField("mChildFragmentManager")
            childFragmentManager.setAccessible(true)
            childFragmentManager.set(this, null)

        } catch (e: NoSuchFieldException) {
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }
}