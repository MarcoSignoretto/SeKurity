package io.github.marcosignoretto.sekurity

import java.io.InputStream
import java.io.OutputStream
import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-14.
 */
interface SymmetricStreamCipher {
    fun encryptStream(secretKey: SecretKey, plainStream: InputStream, encryptedStream: OutputStream)
    fun decryptStream(secretKey: SecretKey, encryptedStream: InputStream, plainStream: OutputStream)
}