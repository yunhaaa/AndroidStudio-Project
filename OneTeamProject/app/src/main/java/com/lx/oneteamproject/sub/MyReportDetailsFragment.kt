package com.lx.oneteamproject.sub

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.databinding.FragmentMyReportDetailsBinding
import com.lx.oneteamproject.fragment.OnFragmentListener

class MyReportDetailsFragment : Fragment() {
    var _binding: FragmentMyReportDetailsBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyReportDetailsBinding.inflate(inflater, container, false)

        binding.myreportpager.adapter =
            MyReportDeatailsPhotoPagerAdapter(requireActivity().supportFragmentManager, lifecycle)

        return binding.root
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