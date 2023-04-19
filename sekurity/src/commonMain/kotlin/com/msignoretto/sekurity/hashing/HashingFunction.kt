package com.msignoretto.sekurity.hashing

import com.msignoretto.sekurity.BinaryData

interface HashingFunction {
    fun digest(value: String): BinaryData
}