package com.lx.oneteamproject.other

data class Message(
    var message: String?,
    var sendId: String?

){
    constructor():this("","")
}
