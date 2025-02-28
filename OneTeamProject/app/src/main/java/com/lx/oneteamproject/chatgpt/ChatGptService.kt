package com.lx.oneteamproject.chatgpt

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ChatGPTService {
    @POST("/v1/engines/davinci-codex/completions")
    fun getCompletion(
        @Header("Authorization") apiKey: String?,
        @Body input: ChatInput?
    ): Call<ResponseBody?>?
}
