package com.lx.oneteamproject.sub

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lx.oneteamproject.databinding.MyReportListItemBinding

class MyReportListAdapter : RecyclerView.Adapter<MyReportListAdapter.ViewHolder>() {

    // 아이템으로 보여줄 것들을 담아둠
    var myreportlistitems = ArrayList<MyReportListItem>()

    lateinit var listener: OnMyReportListItemClickListener

    override fun getItemCount(): Int = myreportlistitems.size

    // 화면에 보여줄 아이템 모양이 처음 만들어질 때 자동 호출됨
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MyReportListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myreportitem = myreportlistitems[position]
        holder.setItem(myreportitem)
    }

    inner class ViewHolder(val binding: MyReportListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                listener.onMyReportListItemClick(this, binding.root, adapterPosition)
            }
        }

        // 보여줄 아이템 모양에 데이터 설정하기
        fun setItem(item: MyReportListItem) {
            val reporttitle = binding.reporttitle
            reporttitle.setText(item.reporttitle)

            val reportdate = binding.reportdate
            reportdate.setText(item.reportdate)

            val reportlocation = binding.reportlocation
            reportlocation.text = item.reportlocation

            val reportprogress = binding.reportprogress
            reportprogress.text = item.reportprogress

            Glide.with(binding.reportimage).load(item.reportimage).into(binding.reportimage)

        }

    }

}