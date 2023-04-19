package com.msignoretto.sekurity.hashing

import com.msignoretto.sekurity.BinaryData
import com.msignoretto.sekurity.toBinaryData
import java.security.MessageDigest

class SHA3HashingFunction : HashingFunction {
    override fun digest(value: String): BinaryData {
        // TODO handle salt here to more secure hashing
        val digest = MessageDigest.getInstance("SHA-512").digest(value.toByteArray(Charsets.UTF_8))
            .toTypedArray()
        return digest.toByteArray().toBinaryData()
    }

}