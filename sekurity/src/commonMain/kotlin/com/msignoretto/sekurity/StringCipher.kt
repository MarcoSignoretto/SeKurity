package com.msignoretto.sekurity

interface StringCipher {
    fun encrypt(securityKey: SecurityKey, plainData: String): BinaryData
    fun decrypt(securityKey: SecurityKey, encryptedData: BinaryData): String
}