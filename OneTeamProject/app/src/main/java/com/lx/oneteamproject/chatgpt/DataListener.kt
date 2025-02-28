package com.lx.oneteamproject.chatgpt

// 데이터 전달을 위한 인터페이스 정의
interface DataListener {
    fun onDataReceived(data: String)
}
