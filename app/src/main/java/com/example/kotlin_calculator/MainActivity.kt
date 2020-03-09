@file:Suppress("RemoveRedundantCallsOfConversionMethods", "unused")
package com.example.kotlin_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import androidx.annotation.Keep


class MainActivity : AppCompatActivity() {
    private var operator: String? = null
    private var equalFlag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnClear.onClick {
            calcScreenExp.text = "0"
            calcScreenResult.text = ""
        }
        btnDelete.onClick {
            val screen : String? = calcScreenExp.text.toString()
            if (screen?.length !=0)
                calcScreenExp.text = screen?.subSequence(0,(screen.length)-1)
        }
        btnPoint.onClick {
            var screen = calcScreenExp.text.toString()
            if ("." !in screen)
                screen += "."
        }
        btnEqual.onClick {
            val screenText : String? = calcScreenExp.text.toString()
            val str = operator?.let { it1 -> screenText?.split(it1) }
            if (str?.size == 2 && str[0] != "" && str[1] != "") {
                calcScreenResult.text = performOperation(str).toString()
                equalFlag = true
            }
//            TODO("ex- 2+ is invalid")
        }

//        TODO() get the screen width and show the button

    }
    private fun performOperation(str: List<String>): Double? {
//        longToast("${str[0].toString()},${str[1].toString()}")
//        TODO("you can stop multiple operation")
        val a: Double = str[0].toString().toDouble()
        val b: Double = str[1].toString().toDouble()
//        Log.d("DEBUG","$str[0] $operator $str[1]")
        var result = 0.0
        when(operator) {
            "+" -> result = a+b
            "-" -> result = a-b
            "*" -> result = a*b
            "/" -> result = a/b
        }
        return result
    }

    @Keep
    fun numberBtnOnClick(view: View?) {
        var screen = calcScreenExp.text.toString()
        val button = view as Button
        val btnDigit = button.text
        when {
            screen == "0" -> {
                calcScreenExp.text = ""
                calcScreenResult.text = ""
                calcScreenExp.text = btnDigit
            }
            equalFlag -> {
                screen += btnDigit
                calcScreenExp.text = screen
            }
            else -> {
                calcScreenExp.text = ""
                calcScreenResult.text = ""
                calcScreenExp.text = btnDigit
                equalFlag = true
            }
        }
    }

    @Keep
    fun operatorBtnOnClick(view: View?) {
        var screen = calcScreenExp.text.toString()
        val button = view as Button
        operator = button.text.toString()
        val last = lastOperator()
//        toast(isLastDigitOrPoint().toString())
        when {
            isLastDigitOrPoint() && isOperatorUsed() -> //            toast("return")
                return
            isLastDigitOrPoint() -> screen += operator
            last != operator -> {
                screen = screen.dropLast(1)         //change the operator at the current location only
                screen += operator
            }
        }
        calcScreenExp.text = screen

//        TODO("ex- 2+3* is invalid also 2+ is invalid")
//        TODO("ex- 0 on the screen and user press operator or multiple operator pressed")
    }

    private fun isOperatorUsed(): Boolean {
        val screen = calcScreenExp.text.toString()
        val indexPlus = screen.indexOf("+",0)
        val indexMinus = screen.indexOf("-",0)
        val indexMul = screen.indexOf("*",0)
        val indexDivide = screen.indexOf("/",0)
        return (indexPlus >=0 || indexMinus >=0 || indexMul >=0 || indexDivide >=0)
    }

    private fun lastOperator(): String {
        val screen = calcScreenExp.text.toString()
        val len = screen.length
        val last = screen.substring(len-1)
//        toast("last Operator: $last")
        return when(last) {
            "+" -> "+"
            "-" -> "-"
            "*" -> "*"
            "/" -> "/"
            else -> ""
        }
    }

    private fun isLastDigitOrPoint(): Boolean {
        val screen = calcScreenExp.text.toString()
        val len = screen.length
        val last = screen.substring(len-1)
//        toast("last Operator: $last")
        return when(last) {
            "+","-","*","/","." -> false
            else -> true
        }
    }
}