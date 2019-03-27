package com.example.bmi

import android.content.Intent
import com.example.bmi.logic.BmiForKgCm
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bmi.logic.Bmi
import com.example.bmi.logic.BmiForLbIN


import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.math.roundToInt

const val RESULT="0.00"

class MainActivity : AppCompatActivity() {


    var kg_cm = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)



        if(savedInstanceState!=null) {
            mass_text.text = savedInstanceState.getString("mass_text")
            height_text.text = savedInstanceState.getString("height_text")
            result_text.setTextColor(savedInstanceState.getInt("color"))
            kg_cm=savedInstanceState.getBoolean("kg_cm")
            result_text.text=savedInstanceState.getString("result_text")
            bmi_text.text=savedInstanceState.getString("bmi_text")


        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {
            val intent = Intent(this,AboutMe::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_two) {
            changeUnits()
            Toast.makeText(this, "Units changed!", Toast.LENGTH_SHORT).show()
            return true
        }
        if (id == R.id.action_three) {


           return true
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putString("result_text",result_text.text.toString())
        savedInstanceState?.putString("bmi_text",bmi_text.text.toString())
        savedInstanceState?.putInt("color",result_text.currentTextColor)
        savedInstanceState?.putString("height_text",height_text.text.toString())
        savedInstanceState?.putString("mass_text",mass_text.text.toString())
        savedInstanceState?.putBoolean("kg_cm",kg_cm)

    }
    fun onSendMessage(view:View?){


        val massText = mass_edit.text.toString()
        val heightText = height_edit.text.toString()

        if(massText=="0" || heightText=="0"){
            Toast.makeText(this@MainActivity,"wrong data!",Toast.LENGTH_SHORT).show()
        }
        else if(massText!= "" && heightText!="") {
            val massNumber = massText.toInt()
            val heightNumber = heightText.toInt()

            val bmi:Bmi
            val bmi_result:String
            val result:String


            if(kg_cm) {

                bmi = BmiForKgCm(massNumber, heightNumber)
                bmi_result = bmi.returnResult()

                result = String.format(Locale.ENGLISH,"%.2f", bmi.countBmi())
            }
            else {
                bmi = BmiForLbIN(massNumber, heightNumber)
                bmi_result = bmi.returnResult()

                result = String.format(Locale.ENGLISH,"%.2f", bmi.countBmi())

            }

            result_text.text = result
            bmi_text.text = bmi_result



            if(bmi_result =="UNDERWEIGHT") {
                result_text.setTextColor(ContextCompat.getColor(this,R.color.LAPISLAZULI))
            }
            if(bmi_result=="OBESE"){
                result_text.setTextColor(ContextCompat.getColor(this,R.color.GRYNSZPAN))

            }
            if(bmi_result=="OVERWEIGHT"){
                result_text.setTextColor(ContextCompat.getColor(this,R.color.RÓŻPOMPEJAŃSKI))
            }
            if (bmi_result=="NORMAL") {
                result_text.setTextColor(ContextCompat.getColor(this, R.color.BLACK))
            }
        }
         else
            Toast.makeText(this@MainActivity,"no data!",Toast.LENGTH_SHORT).show()


        if(massText=="0"){
            mass_edit.error = "wrong mass!"
        }
        if(heightText=="0"){
            height_edit.error="wrong height!"
        }
        if(massText==""){
            mass_edit.error="no mass data!"
        }
        if(heightText==""){
            height_edit.error="no height data!"
        }


        }

   private fun changeUnits(){

       val mass_scaler:Double
       val height_scaler:Double

        if(kg_cm){
            height_text.text=getString(R.string.height_in)
            mass_text.text=getString(R.string.mass_lb)

            mass_scaler= 2.20462
            height_scaler=0.39370079

            kg_cm=false
        }
        else {
            height_text.text=getString(R.string.height_cm)
            mass_text.text=getString(R.string.mass_kg)

            mass_scaler = 0.45359237
            height_scaler = 2.54

            kg_cm=true

        }


       if(mass_edit.text.toString()!="" ) {
           mass_edit.setText((mass_edit.text.toString().toInt()*mass_scaler).roundToInt().toString())
       }
       if(height_edit.text.toString()!=""){
           height_edit.setText((height_edit.text.toString().toInt()*height_scaler).roundToInt().toString())
       }
    }

    fun infoClick(view: View?){
        if(result_text.text.toString()!="0.00") {
            val message = result_text.text.toString()
            val intent = Intent(this, Info::class.java).apply {
                putExtra(RESULT, message)
            }
            startActivity(intent)
        }
        else
            Toast.makeText(this, "No info!", Toast.LENGTH_SHORT).show()
    }
}
