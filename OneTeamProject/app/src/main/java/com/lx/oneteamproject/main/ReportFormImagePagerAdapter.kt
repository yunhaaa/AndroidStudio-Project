package com.lx.oneteamproject.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lx.oneteamproject.R

class ReportFormImagePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    val images = arrayListOf<Int>(R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon, R.drawable.imagepulsicon)

    override fun getItemCount(): Int = 10

    override fun createFragment(position: Int): Fragment {
        var fragment = ReportFormImagePagerItemFragment()

        fragment.arguments = Bundle().apply {
            putInt("position", position)
            putInt("image", images[position])
        }

        return fragment
    }
}