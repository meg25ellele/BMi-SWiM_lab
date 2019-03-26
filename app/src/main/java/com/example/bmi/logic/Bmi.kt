package com.example.bmi.logic

interface Bmi {
    fun countBmi(): Double
    fun returnResult(): String {
        val bmi:Double=countBmi()
        var result = "NORMAL"



        if (bmi < 18.5 ){
            result = "UNDERWEIGHT"

        }
        if (bmi > 25.0 && bmi<29.9){
            result = "OVERWEIGHT"
        }
        if (bmi>=29.9){
            result="OBESE"
        }

        return result
    }
}
