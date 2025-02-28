package com.lx.oneteamproject.popup

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import com.lx.oneteamproject.R

class CustomDialog(context: Context, Interface: CustomerDialogInterface) : Dialog(context) {

    // 액티비티에서 인터페이스를 받아옴
    private var customDialogInterface: CustomerDialogInterface = Interface

    private lateinit var addButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.number_connection_popup)

        addButton = findViewById(R.id.emergencyButton)


        // 배경을 투명하게함
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 추가 버튼 클릭 시 onAddButtonClicked 호출 후 종료
        addButton.setOnClickListener {
            customDialogInterface.onAddButtonClicked()
            dismiss()
        }

        // 취소 버튼 클릭 시 onCancelButtonClicked 호출 후 종료

    }
}