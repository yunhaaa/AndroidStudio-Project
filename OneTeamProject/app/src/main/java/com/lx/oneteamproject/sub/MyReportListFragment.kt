package com.lx.oneteamproject.sub

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lx.oneteamproject.databinding.FragmentMyReportListBinding
import com.lx.oneteamproject.fragment.OnFragmentListener
import com.lx.oneteamproject.R

class MyReportListFragment : Fragment() {
    var _binding: FragmentMyReportListBinding? = null
    val binding get() = _binding!!

    lateinit var myreportlistadapter: MyReportListAdapter

    var listener: OnFragmentListener? = null


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        if (activity is OnFragmentListener) {
            listener = activity as OnFragmentListener
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyReportListBinding.inflate(inflater, container, false)

        binding.myreportlist.layoutManager = LinearLayoutManager(requireContext())
        myreportlistadapter = MyReportListAdapter()
        binding.myreportlist.adapter = myreportlistadapter


        myreportlistadapter.listener = object : OnMyReportListItemClickListener {
            override fun onMyReportListItemClick(
                holder: MyReportListAdapter.ViewHolder,
                view: View?,
                position: Int
            ) {
                val item = myreportlistadapter.myreportlistitems[position]

                // 새 프래그먼트 인스턴스를 생성
                val targetFragment = MyReportDetailsFragment()

                // 현재 프래그먼트를 새 프래그먼트로 교체
                parentFragmentManager.beginTransaction()
                    .replace(R.id.myreportlistfragmentcontainer, targetFragment)
                    .addToBackStack(null)
                    .commit()
            }

        }

        myreportlistadapter.myreportlistitems.add(MyReportListItem("학동역 10번출구 앞 가로등 고장으로 공사 요청", "강남구 논현동", "23-11-17", "처리중", R.drawable.report_sample))

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