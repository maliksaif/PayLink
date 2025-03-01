package com.pay.link.domain.models

import java.io.Serializable

data class Account(
    val id : Int,
    val holder : String,
    val number : String,
    val balance : Double
) : Serializable