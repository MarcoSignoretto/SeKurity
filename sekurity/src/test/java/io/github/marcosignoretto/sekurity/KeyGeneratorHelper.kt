package io.github.marcosignoretto.sekurity

import java.security.SecureRandom
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-09-06.
 */
fun generateKey(): SecretKey {
    val keyGen = KeyGenerator.getInstance("AES")
    val secRandom = SecureRandom()
    keyGen.init(secRandom)
    return keyGen.generateKey()
}