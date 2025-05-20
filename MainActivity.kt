package com.example.angleconverter

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var degreeEditText: EditText
    private lateinit var radianEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var convertToRadianButton: Button
    private lateinit var convertToDegreeButton: Button
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        degreeEditText = findViewById(R.id.degree_edit_text)
        radianEditText = findViewById(R.id.radian_edit_text)
        resultTextView = findViewById(R.id.result_text_view)
        convertToRadianButton = findViewById(R.id.convert_to_radian_button)
        convertToDegreeButton = findViewById(R.id.convert_to_degree_button)
        clearButton = findViewById(R.id.clear_button)

        convertToRadianButton.setOnClickListener {
            convertToRadian()
        }

        convertToDegreeButton.setOnClickListener {
            convertToDegree()
        }

        clearButton.setOnClickListener {
            clearResult()
        }
    }

    private fun convertToRadian() {
        val degree = degreeEditText.text.toString().toDouble()
        val radian = degree * Math.PI / 180
        resultTextView.text = "$degree degrees is equal to $radian radians"
    }

    private fun convertToDegree() {
        val radian = radianEditText.text.toString().toDouble()
        val degree = radian * 180 / Math.PI
        resultTextView.text = "$radian radians is equal to $degree degrees"
    }

    private fun clearResult() {
        resultTextView.text = ""
        degreeEditText.text.clear()
        radianEditText.text.clear()
    }
}