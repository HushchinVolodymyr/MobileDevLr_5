package com.example.lr_5

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val powerSystemCalculator = PowerSystemCalculator()

        val resultOutput: TextView = findViewById(R.id.resultOutput)

        val elGasActivityInput: EditText = findViewById(R.id.elGasActivityInput)
        val PLInput: EditText = findViewById(R.id.PlInput)
        val PLLongInput: EditText = findViewById(R.id.PlLongInput)
        val transmissionInput: EditText = findViewById(R.id.transmissionInput)
        val activatorInput: EditText = findViewById(R.id.activatorInput)
        val connectionInput: EditText = findViewById(R.id.connectionInput)
        val connectionTimesInput: EditText = findViewById(R.id.connectionTimesInput)

        val costAvarInput: EditText = findViewById(R.id.costAvarInput)
        val costPlanInput: EditText = findViewById(R.id.costPlanInput)

        val calculateButton: Button = findViewById(R.id.calculateButton)

        calculateButton.setOnClickListener {
            val elGasActiv = elGasActivityInput.text.toString().toDouble() ?: 1.0
            val PL = PLInput.text.toString().toDouble() ?: 1.0
            val PLLong = PLLongInput.text.toString().toInt() ?: 1
            val transmission = transmissionInput.text.toString().toDouble() ?: 1.0
            val activ = activatorInput.text.toString().toDouble() ?: 1.0
            val connection = connectionInput.text.toString().toDouble() ?: 1.0
            val connectionTimes = connectionTimesInput.text.toString().toDouble() ?: 1.0

            val costAvar = costAvarInput.text.toString().toDouble() ?: 1.0
            val costPlan = costPlanInput.text.toString().toDouble() ?: 1.0

            val transmissionSystem = TransmissionSystem(
                elGasActiv,
                PL,
                PLLong,
                transmission,
                activ,
                connection,
                connectionTimes
            )

            val losses = Losses(
                costAvar,
                costPlan
            )


            resultOutput.text = """
                Single circuit reliability: ${powerSystemCalculator.calcReliable(transmissionSystem)}
                Losses: ${powerSystemCalculator.calculateLoss(losses).toInt()} UAH
            """.trimIndent()
        }
    }
}