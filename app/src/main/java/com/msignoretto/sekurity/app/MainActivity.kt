package com.msignoretto.sekurity.app

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.msignoretto.sekurity.*
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {

    lateinit var stringCipher: StringCipher
    lateinit var appKeyStore: KeyStore
    lateinit var key: SecretKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stringCipher = StringCipherImpl(cipher = AESCipher())
        appKeyStore = AppKeyStore(this)
        key = appKeyStore.getOrGenerate().toSecretKey()
    }
    fun encrypt(@Suppress("UNUSED_PARAMETER") view: View) {
        val text = findViewById<EditText>(R.id.clear_input).text.toString()
        val encrypted = stringCipher.encrypt(key.toSecurityKey(), text)
        val stringEnc = Base64.encodeToString(encrypted.toByteArray(), Base64.NO_WRAP)
        findViewById<TextView>(R.id.encrypted_text).text = stringEnc
    }

    fun decrypt(@Suppress("UNUSED_PARAMETER") view: View) {
        val stringEnc = findViewById<EditText>(R.id.cipher_input).text.toString()
        val text = stringCipher.decrypt(key.toSecurityKey(), Base64.decode(stringEnc, Base64.NO_WRAP).toBinaryData())
        findViewById<TextView>(R.id.decrypted_text).text = text
    }
}
