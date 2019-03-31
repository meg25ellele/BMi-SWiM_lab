package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.logic.HistoryAdapter
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        bmi_history_recyclerView.layoutManager =  LinearLayoutManager(this)
        bmi_history_recyclerView.adapter = HistoryAdapter()

    }


}
