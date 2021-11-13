package com.ssafy.jobis.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ssafy.jobis.R
import com.ssafy.jobis.presentation.calendar.RoutineScheduleFragment
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlinx.android.synthetic.main.fragment_single_schedule.*


class CalendarScheduleActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        var myData = intent.getStringExtra("my_data")
        println("액티비티 데이터 전달: " + myData)


        scheduleTopAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.schedule_item1 -> {
                    println("저장 체크 클릭함")
                    var test : Fragment = supportFragmentManager.findFragmentById(R.id.scheduleFrameLayout) as Fragment
                    println(test)
                    println("이게뭐람")
                    println(test.startDate.toString())

                    true
                }
                else -> false
            }
        }


        var spinner: Spinner = scheduleSpinner
        ArrayAdapter.createFromResource(this,
            R.array.routine,
            android.R.layout.simple_spinner_item).also{adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0){
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.scheduleFrameLayout, singleScheduleFragement(this@CalendarScheduleActivity))
                        .commit()
                } else if (position == 1) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.scheduleFrameLayout, RoutineScheduleFragment(this@CalendarScheduleActivity))
                        .commit()
                }
            }
        }

    }
}

