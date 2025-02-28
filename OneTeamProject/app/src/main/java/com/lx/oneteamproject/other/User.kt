package com.lx.oneteamproject.other

data class User(
    var name: String,
    var email: String,
    var uid: String,
    var mobile: String
){
    constructor(): this("","","","")
}
