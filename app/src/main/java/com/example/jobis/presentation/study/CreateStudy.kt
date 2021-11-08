package com.example.jobis.presentation.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.jobis.R
import com.example.jobis.databinding.ActivityCreateStudyBinding

class CreateStudy : AppCompatActivity() {

    private var mBinding: ActivityCreateStudyBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val region_list = resources.getStringArray(R.array.study_region)
        val region_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,region_list)

        binding.spRegion.adapter = region_adapter

        var region_data:String? = null

        binding.spRegion.onItemClickListener = object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> {
                        region_data = null
                    }
                    1 -> {
                        region_data = "1"
                    }
                    2 -> {
                        region_data = "2"
                    }
                    3 -> {
                        region_data = "3"
                    }
                    4 -> {
                        region_data = "4"
                    }
                    5 -> {
                        region_data = "5"
                    }
                    6 -> {
                        region_data = "6"
                    }
                    7 -> {
                        region_data = "7"
                    }
                    8 -> {
                        region_data = "8"
                    }
                    else -> {
                        region_data = null
                    }
                }

                Log.d("region_text","지역 : ${region_data}")
            }
        }


    }

}