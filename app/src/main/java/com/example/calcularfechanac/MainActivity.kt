package com.example.calcularfechanac

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var selectedDateText : TextView? = null
    private var minutesText : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker : Button = findViewById(R.id.btnDatePicker)
        selectedDateText = findViewById(R.id.selectedDate)
        minutesText = findViewById(R.id.finalMinutesText)

        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    //Date picker code
    private fun clickDatePicker(){
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        //dpd = Date Picker Dialog
        val dpd = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"
                selectedDateText?.text = selectedDate

                //Format the text to get a date
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                //Get the time between the selected date and today in minutes
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000
                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes
                        minutesText?.text = differenceInMinutes.toString()
                    }
                }
            },
            year,
            month,
            day)

        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 //1 hour in milSec
        dpd.show()
    }


}