package com.sample.smsFinder.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.sample.smsFinder.R
import com.sample.smsFinder.model.SMS
import com.sample.smsFinder.utils.Constants
import kotlinx.android.synthetic.main.activity_chart.*
import java.util.regex.Matcher
import java.util.regex.Pattern


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ChartActivity : AppCompatActivity() {
    private var chartList: ArrayList<SMS> = arrayListOf()
    /**
     * This is REGX of DecimalPoint Finder
     */
    private var regexDecimalFinder = "(\\d+(?:\\.\\d+))"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)
        this.supportActionBar?.title = getString(R.string.label_pie_chart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        chartList = intent.getParcelableArrayListExtra(Constants.KEY_DATA)
        findSMSContains()

    }
    /**
     * Here Find the sms contains
     */
    private fun findSMSContains() {
        var income = 0.0
        var expanse = 0.0
        for (i in 0 until chartList.size) {
            val message = chartList[i].message
            if (checkExpanseContains(message)) {
                expanse += filterIncomeExpanse(message)
            } else if (checkIncomeContains(message)) {
                income += filterIncomeExpanse(message)
            }
        }
        setChartData(income, expanse)
    }

    /**
     * Here Check Income Messages Contain
     */
    private fun checkIncomeContains(message: String): Boolean {
        return message.contains(Constants.KEYWORD_CREDIT) || message.contains(Constants.KEYWORD_CREDITED) ||
                message.contains(Constants.KEYWORD_RECEIVE) || message.contains(Constants.KEYWORD_RECEIVED)
    }

    /**
     * Here Check Expanse Messages Contain
     */
    private fun checkExpanseContains(message: String): Boolean {
        return message.contains(Constants.KEYWORD_DEBITED) || message.contains(Constants.KEYWORD_DEBIT) ||
                message.contains(Constants.KEYWORD_PURCHASING) ||
                message.contains(Constants.KEYWORD_PURCHASE)
    }

    /**
     * Here Filter the and get Amount of Income and Expanse
     */
    private fun filterIncomeExpanse(message: String): Double {
        val pattern: Pattern = Pattern.compile(regexDecimalFinder)
        val matcher: Matcher = pattern.matcher(message)
        while (matcher.find()) {
            return matcher.group(1).toDouble()
        }
        return 0.0
    }

    private fun setChartData(income: Double, expanse: Double) {
        pieChart.setUsePercentValues(false)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(10f, 10f, 10f, 10f)
        pieChart.dragDecelerationFrictionCoef = 0.9f
        pieChart.setHoleColor(Color.WHITE)

        /**
         * Here add the Income Expanse Value to Pie Chart
         */
        val yValues: ArrayList<PieEntry> = ArrayList()
        yValues.add(PieEntry(income.toFloat(), getString(R.string.label_income)))
        yValues.add(PieEntry(expanse.toFloat(), getString(R.string.label_expanse)))

        /**
         * Here set Dataset (set the color of Pie chat graph)
         */
        val dataSet = PieDataSet(yValues, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = mutableListOf(
            Color.rgb(0, 128, 0),
            Color.rgb(255, 0, 0)
        )

        val pieData = PieData((dataSet))
        pieData.setValueTextSize(15f)
        pieData.setValueTextColor(Color.YELLOW)
        pieChart.data = pieData
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}