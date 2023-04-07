package com.msignoretto.sekurity


/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-08.
 */
class StringCipherImpl constructor(
    private val cipher: SymmetricCipher
) : StringCipher {
    override fun encrypt(securityKey: SecurityKey, plainData: String): BinaryData =
        cipher.encrypt(securityKey, plainData.encodeToByteArray().toBinaryData())

    override fun decrypt(securityKey: SecurityKey, encryptedData: BinaryData): String =
        cipher.decrypt(securityKey, encryptedData.toByteArray().toBinaryData()).toByteArray()
            .decodeToString()
}