package com.lx.oneteamproject.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lx.oneteamproject.R

class MyReportDeatailsPhotoPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    val images = arrayListOf<Int>(R.drawable.report_sample, R.drawable.sample_report2, R.drawable.sample_report3)

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = MyReportDetailsPhotoPagerItemFragment()

        fragment.arguments = Bundle().apply {
            putInt("position", position)
            putInt("image", images[position])
        }

        return fragment
    }
}