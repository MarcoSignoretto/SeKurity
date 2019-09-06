package io.github.marcosignoretto.sekurity

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.SecretKey

class MainActivity : AppCompatActivity() {

    lateinit var stringCipher: StringCipher
    lateinit var appKeyStore: AppKeyStore
    lateinit var key: SecretKey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stringCipher = StringCipher(cipher = AESCipher())
        appKeyStore = AppKeyStore(this)
        key = appKeyStore.getOrGenerate()
    }

    fun encrypt(view: View) {
        val text = findViewById<EditText>(R.id.clear_input).text.toString()
        val encrypted = stringCipher.encrypt(key, text)
        val stringEnc = Base64.encodeToString(encrypted, Base64.NO_WRAP)
        findViewById<TextView>(R.id.encrypted_text).text = stringEnc
    }

    fun decrypt(view: View) {
        val stringEnc = findViewById<EditText>(R.id.cipher_input).text.toString()
        val text = stringCipher.decrypt(key, Base64.decode(stringEnc, Base64.NO_WRAP))
        findViewById<TextView>(R.id.decrypted_text).text = text
    }
}
