package io.github.marcosignoretto.sekurity

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import java.util.*
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

/**
 * Created by
 * Marco Signoretto
 * Android Developer
 * on 2019-07-08.
 */
class AppKeyStore constructor(
    context: Context
) {

    private val keyStore: KeyStore
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
        keyStore = KeyStore.getInstance(KEY_STORE)
        keyStore.load(null)
    }

    fun getOrGenerate(): SecretKey {
        return if (keyAlias in keyStore.aliases().toList()) {
            retrieveKey(keyAlias)
        } else {
            generateKey(keyAlias)
        }
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

        val secretKey = keyGenerator.generateKey()
        return secretKey
    }

    private fun retrieveKey(alias: String): SecretKey {
        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        return secretKeyEntry.secretKey
    }

    companion object {
        private const val KEY_STORE = "AndroidKeyStore"
        private const val ALIAS_PREFS = "AliasPreferences"
        private const val ALIAS_KEY = "ALIAS_KEY"
    }
}