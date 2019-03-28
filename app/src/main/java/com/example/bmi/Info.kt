package com.example.bmi

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info.*

class Info : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_info)

        setSupportActionBar(toolbar3)


        val message= intent.getStringExtra(RESULT)
        result_view.append("\n"+message)

        changeText(message)

    }

    private fun changeText(value:String){

        val bmiNumber=value.toDouble()
        var changedMessage:String = getString(R.string.edit_NORMA)


        if (bmiNumber < 18.5 ) {
            changedMessage =getString(R.string.edit_UNDERWEIGHT)
        }
        if (bmiNumber > 25.0 && bmiNumber<29.9){
            changedMessage =getString(R.string.edit_OVERWEIGHT)
        }
        if (bmiNumber>=29.9){
            changedMessage=getString(R.string.edit_OBESE)

        }


        info_view.text=changedMessage

    }
}
