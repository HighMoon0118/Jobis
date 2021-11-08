package com.example.jobis.presentation.chat.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.jobis.presentation.chat.DrawingFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

    val df: ArrayList<DrawingFragment> = ArrayList()

    init {
        df.add(DrawingFragment())
        df.add(DrawingFragment())
        df.add(DrawingFragment())
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return df[position]
    }

    fun addView(position: Int) {
        df[position].addView()
    }

    fun saveView(position: Int) {
        df[position].saveView()
    }
}