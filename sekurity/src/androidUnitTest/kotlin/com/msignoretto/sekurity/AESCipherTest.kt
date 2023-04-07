package com.msignoretto.sekurity

import junit.framework.TestCase.*
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.random.Random

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-09-06.
 */
class AESCipherTest {

    private val cipher = AESCipher()

    @Test
    fun encryptDecryption() {
        val key = generateKey()
        val plainData = Random.Default.nextBytes(23).toBinaryData()

        val cipherData = cipher.encrypt(key.toSecurityKey(), plainData)
        val plainDataBack = cipher.decrypt(key.toSecurityKey(), cipherData).toByteArray()

        assertTrue(plainData.toByteArray().contentEquals(plainDataBack))
    }


    @Test
    fun encryptDecryptionStream() {
        val key = generateKey()

        val plainData = Random.nextBytes(23)
        val plainStream = ByteArrayInputStream(plainData)
        val encryptedStream = ByteArrayOutputStream(400)

        val plainStreamResult = ByteArrayOutputStream(400)

        cipher.encryptStream(key, plainStream, encryptedStream)
        cipher.decryptStream(key, ByteArrayInputStream(encryptedStream.toByteArray()), plainStreamResult)

        assertTrue(plainData.contentEquals(plainStreamResult.toByteArray()))
    }

    @Test
    fun differentCipherDataIfEncryptedTwice() {
        val plainData = Random.Default.nextBytes(23).toBinaryData()
        val key = generateKey()

        val cipherData = cipher.encrypt(key.toSecurityKey(), plainData)
        val plainDataBack = cipher.decrypt(key.toSecurityKey(), cipherData).toByteArray()

        assertTrue(plainData.toByteArray().contentEquals(plainDataBack))

        val cipherData2 = cipher.encrypt(key.toSecurityKey(), plainData).toByteArray()
        val plainDataBack2 = cipher.decrypt(key.toSecurityKey(), cipherData).toByteArray()

        assertTrue(plainData.toByteArray().contentEquals(plainDataBack2))
        assertFalse(cipherData.toByteArray().contentEquals(cipherData2))
    }
}