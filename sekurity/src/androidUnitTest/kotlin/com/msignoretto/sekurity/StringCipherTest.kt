package com.msignoretto.sekurity

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-09-06.
 */
class StringCipherTest {

    private val stringCipher = StringCipherImpl(cipher = AESCipher())

    @Test
    fun encryptDecrypt() {
        val key = generateKey()
        val plainText = "I'm A plain text"

        val encryptedData = stringCipher.encrypt(key.toSecurityKey(), plainText)
        val plainTextResult = stringCipher.decrypt(key.toSecurityKey(), encryptedData)

        assertEquals(plainText, plainTextResult)
    }
}