package com.pay.link.domain.models

data class Account(
    val id : Int,
    val holder : String,
    val number : String,
    val balance : Double
)