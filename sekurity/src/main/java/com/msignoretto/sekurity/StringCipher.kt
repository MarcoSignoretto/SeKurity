package com.msignoretto.sekurity

import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-08.
 */
class StringCipher constructor(
    private val cipher: SymmetricCipher
) {
    fun encrypt(secretKey: SecretKey, plainData: String): ByteArray =
        cipher.encrypt(secretKey, plainData.toByteArray(StandardCharsets.UTF_8))

    fun decrypt(secretKey: SecretKey, encryptedData: ByteArray): String =
        cipher.decrypt(secretKey, encryptedData).toString(StandardCharsets.UTF_8)
}