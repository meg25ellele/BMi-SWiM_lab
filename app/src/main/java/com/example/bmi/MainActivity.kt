package com.example.bmi


import android.content.Intent
import android.content.SharedPreferences
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

const val RESULT="0.00"



class MainActivity : AppCompatActivity() {


    companion object {

        //consts used in SharecPreferences(History)

        const val HISTORY_AMOUNT = 10
        const val DATE = "date"
        const val MASS = "mass"
        const val HEIGHT = "height"
        const val RESULT_NUMB = "resultNumb"
        const val RESULT_TEXT = "resultText"
        const val COLOR = "color"
        const val SAVED_NUMBER = "main_key"

        var remembered: SharedPreferences? = null
    }


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
            Toast.makeText(this, getString(R.string.toast_changed), Toast.LENGTH_SHORT).show()
            return true
        }
        if (id == R.id.action_three) {
            val intent = Intent(this,History::class.java)
            startActivity(intent)
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
            Toast.makeText(this@MainActivity,getString(R.string.toast_wrongdata),Toast.LENGTH_SHORT).show()
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


            save()

        }
         else
            Toast.makeText(this@MainActivity,getString(R.string.toast_nodata),Toast.LENGTH_SHORT).show()


        if(massText=="0"){
            mass_edit.error = getString(R.string.wrong_mass)
        }
        if(heightText=="0"){
            height_edit.error=getString(R.string.wrong_height)
        }
        if(massText==""){
            mass_edit.error=getString(R.string.no_mass)
        }
        if(heightText==""){
            height_edit.error=getString(R.string.no_height)
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


       if(mass_edit.text.toString()!="") {
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
            Toast.makeText(this, getString(R.string.toast_noinfo), Toast.LENGTH_SHORT).show()
    }

    private fun save(){

        remembered = getSharedPreferences("history", 0)
        var historyIndex = remembered!!.getInt(SAVED_NUMBER, 1)
        val editor = remembered?.edit()

        val date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss",Locale.US)
        val dateInString = date.format(Date())


        historyIndex++

        editor?.putString(DATE + historyIndex, dateInString)
        editor?.putString(RESULT_NUMB + historyIndex.toString(), result_text.text.toString())
        editor?.putString(RESULT_TEXT + historyIndex.toString(), bmi_text.text.toString())
        editor?.putInt(COLOR+historyIndex.toString(),result_text.currentTextColor)
        editor?.putInt(SAVED_NUMBER,historyIndex)

        var mass_unit = "kg"
        var height_unit = "cm"
        if (!kg_cm ){
            mass_unit = "lb"
            height_unit = "in"
        }

        editor?.putString(MASS + historyIndex.toString(), mass_edit.text.toString() + mass_unit)
        editor?.putString(HEIGHT + historyIndex.toString(), height_edit.text.toString() + height_unit)


        editor?.apply()


    }
}

