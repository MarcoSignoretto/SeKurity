package com.msignoretto.sekurity

import javax.crypto.SecretKey

internal class SecurityKeyImpl(val secretKey: SecretKey) : SecurityKey

fun SecurityKey.toSecretKey() = (this as SecurityKeyImpl).secretKey
fun SecretKey.toSecurityKey(): SecurityKey = SecurityKeyImpl(this)