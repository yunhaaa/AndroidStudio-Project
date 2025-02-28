package com.lx.oneteamproject.main

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.databinding.ReportDetailsFragmentBinding


class ReportDetailsFragment : Fragment() {

    private var _binding: ReportDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = ReportDetailsFragmentBinding.inflate(inflater, container, false)

        val contentTitle = SpannableString("제목")
        contentTitle.setSpan(UnderlineSpan(), 0, contentTitle.length, 0)
        binding.reportTitle.text = contentTitle

        val contentTime = SpannableString("신고일시")
        contentTime.setSpan(UnderlineSpan(), 0, contentTime.length, 0)
        binding.reportTime.text = contentTime

        val contentPlace = SpannableString("위치 장소")
        contentPlace.setSpan(UnderlineSpan(), 0, contentPlace.length, 0)
        binding.reportPlace.text = contentPlace



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}




