package com.lx.oneteamproject.main

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lx.oneteamproject.databinding.FragmentMainPublicadPagerItemBinding
import androidx.fragment.app.Fragment
import android.view.View
import com.lx.oneteamproject.fragment.OnFragmentListener

class MainPublicAdPagerItemFragment : Fragment() {

    var _binding: FragmentMainPublicadPagerItemBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentMainPublicadPagerItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.apply {
            val position = getInt("position", -1)

            val image = getInt("image")
            binding.mainPublicAdImage.setImageResource(image)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDetach() {
        super.onDetach()

        listener = null
    }
}