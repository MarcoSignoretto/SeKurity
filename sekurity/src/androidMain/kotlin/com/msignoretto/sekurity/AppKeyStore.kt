package com.msignoretto.sekurity

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

private typealias JavaKeyStore = java.security.KeyStore
private typealias JavaSecretKeyEntry = java.security.KeyStore.SecretKeyEntry

class AppKeyStore constructor(
    context: Context
): KeyStore {

    private val keyStore: JavaKeyStore
    private val keyAlias: String

    init {
        var res = context.getSharedPreferences(ALIAS_PREFS, Context.MODE_PRIVATE).getString(
            ALIAS_KEY, null
        )
        if (res == null) {
            res = UUID.randomUUID().toString()
            context.getSharedPreferences(ALIAS_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(ALIAS_KEY, res)
                .apply()
        }
        keyAlias = res
        keyStore = JavaKeyStore.getInstance(KEY_STORE)
        keyStore.load(null)
    }

    override fun getOrGenerate(): SecurityKey {
        return if (keyAlias in keyStore.aliases().toList()) {
            retrieveKey(keyAlias)
        } else {
            generateKey(keyAlias)
        }.toSecurityKey()
    }

    private fun generateKey(alias: String): SecretKey {
        val keyGenerator = KeyGenerator
            .getInstance(KeyProperties.KEY_ALGORITHM_AES, KEY_STORE)

        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        )

        return keyGenerator.generateKey()
    }

    private fun retrieveKey(alias: String): SecretKey {
        val secretKeyEntry = keyStore.getEntry(alias, null) as JavaSecretKeyEntry
        return secretKeyEntry.secretKey
    }

    companion object {
        private const val KEY_STORE = "AndroidKeyStore"
        private const val ALIAS_PREFS = "AliasPreferences"
        private const val ALIAS_KEY = "ALIAS_KEY"
    }
}