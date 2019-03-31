package com.example.bmi.logic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.MainActivity

import com.example.bmi.R
import kotlinx.android.synthetic.main.history_element.view.*

class HistoryAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    var dates = arrayListOf<String>()
    var masses = arrayListOf<String>()
    var heights = arrayListOf<String>()
    var resultsNumb = arrayListOf<String>()
    var resultsText = arrayListOf<String>()
    var colors = arrayListOf<Int>()


    override fun getItemCount(): Int {
        return MainActivity.HISTORY_AMOUNT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {

        var resultsAmount = MainActivity.remembered!!.getInt(MainActivity.SAVED_NUMBER, 0)

        for (i in 0..MainActivity.HISTORY_AMOUNT) {

            dates.add(MainActivity.remembered?.getString(MainActivity.DATE + resultsAmount, "")!!)
            masses.add(MainActivity.remembered?.getString(MainActivity.MASS + resultsAmount, "")!!)
            heights.add(MainActivity.remembered?.getString(MainActivity.HEIGHT + resultsAmount, "")!!)
            resultsNumb.add(MainActivity.remembered?.getString(MainActivity.RESULT_NUMB + resultsAmount, "")!!)
            resultsText.add(MainActivity.remembered?.getString(MainActivity.RESULT_TEXT + resultsAmount, "")!!)
            colors.add(MainActivity.remembered?.getInt(MainActivity.COLOR+resultsAmount,0)!!)
            resultsAmount--
        }


        val layoutInflater= LayoutInflater.from(parent.context)
        val cellForRow  = layoutInflater.inflate(R.layout.history_element,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.mass_view.text = masses[position]
        holder.view.height_view.text=heights[position]
        holder.view.data_view.text=dates[position]
        holder.view.resultNumb_view.text=resultsNumb[position]
        holder.view.resultText_view.text=resultsText[position]
        holder.view.resultNumb_view.setTextColor(colors[position])
    }
}

    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}