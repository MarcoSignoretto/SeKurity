package com.msignoretto.sekurity

import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-13.
 */
interface SymmetricCipher {
    fun encrypt(secretKey: SecretKey, plainData: ByteArray): ByteArray
    fun decrypt(secretKey: SecretKey, encryptedData: ByteArray): ByteArray
}