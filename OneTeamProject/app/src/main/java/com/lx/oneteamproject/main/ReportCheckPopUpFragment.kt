package com.lx.oneteamproject.main

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lx.oneteamproject.fragment.OnFragmentListener
import androidx.fragment.app.Fragment
import com.lx.oneteamproject.databinding.FragmentReportCheckPopupBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class ReportCheckPopUpFragment : Fragment(){

    var _binding: FragmentReportCheckPopupBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null



    override fun onAttach(activity: Activity) {
        super.onAttach(activity)


        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportCheckPopupBinding.inflate(inflater, container, false)

        binding.reportImageOutput.adapter = ReportFreeImagePagerAdapter(requireActivity().supportFragmentManager, lifecycle)


        /*

        binding.reportTitle.hint="sdfsdfsdf"
        val userInput = readUserInputFromFile()

        binding.reportTitle.text = "sdfsdf"
        binding.reportTextOutput.text = "rerereR"
        */

        return binding.root
    }

    private fun readUserInputFromFile(): String {
        var userInput = ""
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptUserInput"
            val fileInputStream = requireContext().openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text).append("\n")
            }
            fileInputStream.close()
            userInput = stringBuilder.toString()
            Log.d("ReadUserInput", "User input read from file: $userInput")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ReadUserInput", "Error reading user input from file: ${e.message}")
        }
        return userInput
    }

    private fun readResultTitleFromFile(): String {
        var resultTitle = ""
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptResultTitle"
            val fileInputStream = requireContext().openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text).append("\n")
            }
            fileInputStream.close()
            resultTitle = stringBuilder.toString()
            Log.d("ReadUserInput", "User input read from file: $resultTitle")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ReadUserInput", "Error reading user input from file: ${e.message}")
        }
        return resultTitle
    }

    private fun readResultCategoryFromFile(): String {
        var resultCategory = ""
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptResultCategory"
            val fileInputStream = requireContext().openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text).append("\n")
            }
            fileInputStream.close()
            resultCategory = stringBuilder.toString()
            Log.d("ReadUserInput", "User input read from file: $resultCategory")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("ReadUserInput", "Error reading user input from file: ${e.message}")
        }
        return resultCategory
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