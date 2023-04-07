package com.msignoretto.sekurity

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-13.
 */
interface SymmetricCipher {
    fun encrypt(securityKey: SecurityKey, plainData: BinaryData): BinaryData
    fun decrypt(securityKey: SecurityKey, encryptedData: BinaryData): BinaryData
}