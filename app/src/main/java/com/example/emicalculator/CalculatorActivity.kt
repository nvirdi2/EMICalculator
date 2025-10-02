package com.example.emicalculator

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.TextView
import kotlin.math.pow
class CalculatorActivity : AppCompatActivity() {
    // Input fields
    private lateinit var loanIn: EditText
    private lateinit var InterestIn: EditText
    private lateinit var tenureIn: EditText
    private lateinit var incomeIn: EditText
    private lateinit var expenseIn: EditText

    // Result displays
    private lateinit var budgetResult: TextView
    private lateinit var EMIresult: TextView

    // Buttons
    private lateinit var budgetBtn: Button
    private lateinit var EMIbtn: Button

    // Store calculated EMI as double since its money
    private var calEMI: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_calculator)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Reference to XML views to variables (VIEWS)
        loanIn = findViewById(R.id.loanInput)
        InterestIn = findViewById(R.id.interestInput)
        tenureIn = findViewById(R.id.tenureInput)
        incomeIn = findViewById(R.id.incomeInput)
        expenseIn = findViewById(R.id.expensesInput)

        EMIresult = findViewById(R.id.emiResult)
        budgetResult = findViewById(R.id.budgetResult)

        budgetBtn = findViewById(R.id.budgetBtn)
        EMIbtn = findViewById(R.id.EMIBtn)

        // Create button action
        EMIbtn.setOnClickListener { calculateEMI() }
        budgetBtn.setOnClickListener { calculateBudget() }
    }

    // Calculates monthly EMI using loan, interest, and tenure
    private fun calculateEMI() {
        val loan = loanIn.text.toString().toDoubleOrNull()
        val annRate = InterestIn.text.toString().toDoubleOrNull()
        val tenMonths = tenureIn.text.toString().toIntOrNull()

        if (loan == null || annRate == null || tenMonths == null || tenMonths <= 0) {
            EMIresult.text = "Invalid input!"
            return
        }

        val monthlyRate = annRate / 12 / 100

        calEMI = if (monthlyRate == 0.0) {
            loan / tenMonths
        }
        else {
            loan * monthlyRate * (1 + monthlyRate).pow(tenMonths) /
                    ((1 + monthlyRate).pow(tenMonths) - 1)
        }

        EMIresult.text = "Monthly EMI: %.2f".format(calEMI)
    }

    // Calculate remaining balance using income, expenses, and EMI
    private fun calculateBudget() {
        val totalIn = incomeIn.text.toString().toDoubleOrNull()
        val totalExp = expenseIn.text.toString().toDoubleOrNull()

        if (totalIn == null || totalExp == null) {
            budgetResult.text = "Invalid input!"
            return
        }

        val balance = totalIn - totalExp - calEMI

        budgetResult.text = if (balance >= 0) {
            "Savings: %.2f".format(balance) // Positive balance (savings)
        } else {
            "Deficit: %.2f".format(balance)  // Negative balance (deficit)
        }
    }
}