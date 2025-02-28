package com.lx.oneteamproject.sub

import android.view.View

interface OnMyReportListItemClickListener {

    fun onMyReportListItemClick(holder: MyReportListAdapter.ViewHolder, view: View?, position: Int)
}