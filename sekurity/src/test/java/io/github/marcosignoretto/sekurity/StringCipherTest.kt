package io.github.marcosignoretto.sekurity

import junit.framework.TestCase.assertEquals
import org.junit.Test

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-09-06.
 */
class StringCipherTest {

    private val stringCipher = StringCipher(cipher = AESCipher())

    @Test
    fun encryptDecrypt() {
        val key = generateKey()
        val plainText = "I'm A plain text"

        val encryptedData = stringCipher.encrypt(key, plainText)
        val plainTextResult = stringCipher.decrypt(key, encryptedData)

        assertEquals(plainText, plainTextResult)
    }
}