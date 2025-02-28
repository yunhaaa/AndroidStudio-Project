package com.lx.oneteamproject.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle
import com.lx.oneteamproject.R

class MainPublicAdPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    val images = arrayListOf<Int>(R.drawable.publicad1, R.drawable.publicad2, R.drawable.publicad3, R.drawable.publicad4)

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        var fragment = MainPublicAdPagerItemFragment()

        fragment.arguments = Bundle().apply {
            putInt("position", position)
            putInt("image", images[position])
        }

        return fragment
    }
}