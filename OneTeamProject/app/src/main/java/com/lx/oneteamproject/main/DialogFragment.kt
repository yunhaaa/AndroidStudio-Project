import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lx.oneteamproject.R
import com.lx.oneteamproject.fragment.FragmentType
import com.lx.oneteamproject.databinding.FragmentReportCheckPopupBinding
import com.lx.oneteamproject.sub.SubActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class ReportCheckPopupFragment : DialogFragment() {

    private var _binding: FragmentReportCheckPopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReportCheckPopupBinding.inflate(inflater, container, false)
        val view = binding.root


        val userInput = readUserInputFromFile()

        binding.reportTitle.text = "adsasdasd"
        /*
        binding.reportTitle.text = readResultTitleFromFile()
        binding.reportTextOutput.text =  readResultCategoryFromFile() + "\n" + userInput
        */

        // "신고하기" 버튼 클릭 시
        binding.button.setOnClickListener {
            // 원하는 동작 수행
            val intent = Intent(requireContext(), SubActivity::class.java).apply {
                putExtra("fragmentType", FragmentType.MYREPORTLIST.name)
            }
            startActivity(intent)

            // 팝업 닫기
            dismiss()
        }

        // "뒤로가기" 버튼 클릭 시
        binding.button2.setOnClickListener {
            // 팝업 닫기
            dismiss()
        }

        return view
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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyDialogStyle)
    }


    override fun onStart() {
        super.onStart()
        val window = dialog?.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        window?.setGravity(Gravity.CENTER)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
