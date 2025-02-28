package com.lx.oneteamproject.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.databinding.FragmentMainReportFreeBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener

class MainReportFreeFragment: Fragment() {

    var _binding: FragmentMainReportFreeBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =  FragmentMainReportFreeBinding.inflate(inflater, container, false)

        // 사용자 입력 받아오기
        binding.reportFreeTextInput.addTextChangedListener {
            // 사용자 입력이 변경될 때마다 reportTextCount에 길이 표시
            val userInput = it.toString()
            val userInputLength = userInput.length

            // reportTextCount에 사용자 입력의 길이를 설정
            binding.reportFreeTextCount.text = userInputLength.toString()

            // 사용자 입력이 변경될 때마다 텍스트 파일에 저장
            saveUserInputToFile(userInput)
        }

        binding.reportFreeImagePager.adapter = ReportFreeImagePagerAdapter(requireActivity().supportFragmentManager, lifecycle)





        binding.reportFormButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.REPORTFORM)
        }

        return binding.root
        }

    private fun saveUserInputToFile(userInput: String) {
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptUserInput"
            val fileOutputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(userInput.toByteArray())
            fileOutputStream.close()
            val filePath = requireContext().getFileStreamPath(fileName).absolutePath
            Log.d("FileOperation", "User input saved to file. Path: $filePath")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FileOperation", "Error saving user input to file: ${e.message}")
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


