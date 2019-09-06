package io.github.marcosignoretto.sekurity

import java.io.InputStream
import java.io.OutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-06.
 */
class AESCipher : SymmetricCipher, SymmetricStreamCipher {

    /**
     * @return pair of iv and encryptedData
     */
    override fun encrypt(secretKey: SecretKey, plainData: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return (cipher.iv).plus(cipher.doFinal(plainData))
    }

    override fun decrypt(secretKey: SecretKey, encryptedData: ByteArray): ByteArray {
        val iv = encryptedData.sliceArray(0 until 12)
        val realEncryptedData = encryptedData.sliceArray(12 until encryptedData.size)
        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        return cipher.doFinal(realEncryptedData)
    }

    override fun encryptStream(
        secretKey: SecretKey,
        plainStream: InputStream,
        encryptedStream: OutputStream
    ) {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        encryptedStream.write(cipher.iv)
        encryptedStream.flush()

        val outputStream = CipherOutputStream(encryptedStream, cipher)

        val buf = ByteArray(BUFFER_SIZE)
        // we are using available because otherwise the read doesn't get -1 at the end of the stream
        while (plainStream.available() > 0) {
            val len =
                plainStream.read(buf, 0, BUFFER_SIZE)// avoid to read more than the maximum capacity
            outputStream.write(buf, 0, len)
        }
        outputStream.flush()

        outputStream.close()
        plainStream.close()
        encryptedStream.close()
    }

    override fun decryptStream(
        secretKey: SecretKey,
        encryptedStream: InputStream,
        plainStream: OutputStream
    ) {
        val ivBuffer = ByteArray(12)
        val ivLen = encryptedStream.read(ivBuffer, 0, 12)
        val iv = ivBuffer.sliceArray(0 until ivLen)
        if (iv.size != 12) throw IllegalArgumentException("Stream decryption failed IV length is invalid")

        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        val inputStream = CipherInputStream(encryptedStream, cipher)

        // I need to allocate the whole space because CipherInputStream read everything in 1 shot in order to verify integrity
        val bufferSize = encryptedStream.available() + BUFFER_SAFE_SPACE
        val buf = ByteArray(bufferSize)

        while (encryptedStream.available() > 0) {
            val len = inputStream.read(buf)
            plainStream.write(buf, 0, len)
        }
        plainStream.flush()
        plainStream.close()

        inputStream.close()
        encryptedStream.close()
    }

    companion object {
        private const val ALGORITHM = "AES/GCM/NoPadding"
        private const val BUFFER_SIZE = 10240
        private const val BUFFER_SAFE_SPACE = 5120
    }
}