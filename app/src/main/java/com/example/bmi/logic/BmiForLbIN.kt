package com.example.bmi.logic

class BmiForLbIN (var mass:Int,var height:Int):Bmi{

    override fun countBmi(): Double {
        val bmi:Double = (mass.toDouble()/ (height*height))*703
        return bmi
    }


}