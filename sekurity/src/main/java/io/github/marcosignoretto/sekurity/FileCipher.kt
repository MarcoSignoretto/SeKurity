package io.github.marcosignoretto.sekurity

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-13.
 */
class FileCipher constructor(
    private val cipher: SymmetricStreamCipher
) {
    /**
     * Encrypt the content of a file and store it in a different file
     * @param plainFile: file with plain content
     * @param encryptedFile: file with encryptedContent
     */
    fun encrypt(secretKey: SecretKey, plainFile: File, encryptedFile: File) {
        val inputStream = FileInputStream(plainFile)
        val outputStream = FileOutputStream(encryptedFile)

        cipher.encryptStream(secretKey, inputStream, outputStream)

        inputStream.close()
        outputStream.close()
    }

    /**
     * Decrypt the content of a file and store it in a different file
     * @param plainFile: file with plain content
     * @param encryptedFile: file with encryptedContent
     */
    fun decrypt(secretKey: SecretKey, encryptedFile: File, plainFile: File) {
        val inputStream = FileInputStream(encryptedFile)
        val outputStream = FileOutputStream(plainFile)

        cipher.decryptStream(secretKey, inputStream, outputStream)

        inputStream.close()
        outputStream.close()
    }
}