package com.lx.oneteamproject.main

import ReportCheckPopupFragment
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lx.oneteamproject.R
import com.lx.oneteamproject.api.BasicClient
import com.lx.oneteamproject.data.GptResponse
import com.lx.oneteamproject.databinding.FragmentMainReportBinding
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.fragment.OnFragmentListener
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainReportFragment : Fragment() {

    var _binding: FragmentMainReportBinding? = null
    val binding get() = _binding!!

    var listener: OnFragmentListener? = null

    private lateinit var loadingDialog: ProgressDialog

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
        _binding = FragmentMainReportBinding.inflate(inflater, container, false)

        loadingDialog = ProgressDialog(requireContext())

        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        val reportfreefragment = MainReportFreeFragment()
        val containerId = R.id.mainReportContainer

        transaction.replace(containerId, reportfreefragment)
        transaction.addToBackStack(null)
        transaction.commit()

        // 신고하기 버튼 눌렀을 때
        binding.reportButton.setOnClickListener{

            // ProgressBar가 나타날 때
            loadingDialog.setMessage("ChatGPT가 생각중이에요!")
            loadingDialog.show()

            // 레이아웃 요소에 터치 차단
            binding.root.isClickable = false
            binding.root.isFocusable = false

            lifecycleScope.launch {
                var userInput = readUserInputFromFile()

                var tmpTitle = getResultTitle(userInput)
                var tmpCategory = getResultCategory(userInput)

                saveUserInputToFile(userInput)
                saveResultTitleToFile(tmpTitle)
                saveResultCategoryToFile(tmpCategory)

                // ProgressBar 숨기기
                loadingDialog.dismiss()

                // 레이아웃 요소에 터치 가능하게 하기
                binding.root.isClickable = true
                binding.root.isFocusable = true

                listener?.onFragmentChanged(FragmentType.REPORTEX)

                val reportCheckPopupFragment = ReportCheckPopupFragment()
                reportCheckPopupFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.MyDialogStyle)
                reportCheckPopupFragment.show(parentFragmentManager, "ReportCheckPopupFragment")
            }



        }

        binding.reportBackButton.setOnClickListener {
            listener?.onFragmentChanged(FragmentType.MAIN)
        }

        return binding.root
    }
    suspend fun getResultCategory(userInput: String): String = suspendCoroutine { continuation ->
        BasicClient.api.requestCategory(word = userInput).enqueue(object : Callback<GptResponse> {
            override fun onResponse(call: Call<GptResponse>, response: Response<GptResponse>) {
                if (response.isSuccessful) {
                    val gptResponse = response.body()
                    val result = gptResponse?.result

                    if (result != null) {
                        // 성공적으로 결과를 받았을 때
                        continuation.resume(result)
                    } else {
                        // 실패한 경우
                        continuation.resume("반환 실패용")
                    }
                } else {
                    // 실패한 경우
                    continuation.resume("반환 실패용")
                }
            }

            override fun onFailure(call: Call<GptResponse>, t: Throwable) {
                // 실패 처리
                continuation.resume("반환 실패용")
            }
        })
    }

    suspend fun getResultTitle(userInput: String): String = suspendCoroutine { continuation ->
        BasicClient.api.requestTitle(word = userInput).enqueue(object : Callback<GptResponse> {
            override fun onResponse(call: Call<GptResponse>, response: Response<GptResponse>) {
                if (response.isSuccessful) {
                    val gptResponse = response.body()
                    val result = gptResponse?.result

                    if (result != null) {
                        // 성공적으로 결과를 받았을 때
                        continuation.resume(result)
                    } else {
                        // 실패한 경우
                        continuation.resume("반환 실패용")
                    }
                } else {
                    // 실패한 경우
                    continuation.resume("반환 실패용")
                }
            }

            override fun onFailure(call: Call<GptResponse>, t: Throwable) {
                // 실패 처리
                continuation.resume("반환 실패용")
            }
        })
    }

    private fun readUserInputFromFile(): String {
        return try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptUserInput"
            val fileInputStream = requireContext().openFileInput(fileName)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }

            fileInputStream.close()
            stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FileOperation", "Error reading user input from file: ${e.message}")
            ""
        }
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

    private fun saveResultTitleToFile(userInput: String) {
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptResultTitle"
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

    private fun saveResultCategoryToFile(userInput: String) {
        try {
            val fileName = "com.lx.oneteamproject.chatgpt.gptResultCategory"
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