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
    }

    override fun getItemCount(): Int {
        return df.size
    }

    override fun createFragment(position: Int): DrawingFragment {
        return df[position]
    }

    fun addFragment(drawingFragment: DrawingFragment) {
        df.add(drawingFragment)
        notifyItemInserted(df.size-1)
    }

    fun addView(position: Int) {
        df[position].addView()
    }

    fun saveView(position: Int) {
        df[position].saveView()
    }
}