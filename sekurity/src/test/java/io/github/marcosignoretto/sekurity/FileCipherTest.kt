package io.github.marcosignoretto.sekurity

import junit.framework.TestCase.*
import org.junit.Test
import java.io.File
import java.nio.charset.Charset

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-09-06.
 */
class FileCipherTest {

    private val fileCipher = FileCipher(cipher = AESCipher())

    @Test
    fun encryptDecrypt() {
        val key = generateKey()
        val text = "I'm a text in a file"
        val file = File.createTempFile("test", null)
        file.writeText(text, Charset.defaultCharset())

        val fileOutput = File.createTempFile("testEncrypt", null)
        val fileResult = File.createTempFile("testResult", null)

        fileCipher.encrypt(key, file, fileOutput)
        fileCipher.decrypt(key, fileOutput, fileResult)

        assertEquals(text, fileResult.readText())
    }
}