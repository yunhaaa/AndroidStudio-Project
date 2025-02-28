package com.lx.oneteamproject.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.databinding.FragmentMainReportFormBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener

class MainReportFormFragment : Fragment() {
    private var _binding: FragmentMainReportFormBinding? = null
    private val binding get() = _binding!!
    private var listener: OnFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainReportFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.reportFormImagePager.adapter =
            ReportFreeImagePagerAdapter(childFragmentManager, lifecycle)

        binding.reportFreeButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.REPORTFREE)
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
