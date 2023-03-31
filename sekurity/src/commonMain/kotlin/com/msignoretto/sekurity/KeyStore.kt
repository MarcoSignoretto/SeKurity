package com.msignoretto.sekurity

interface KeyStore {
    fun getOrGenerate() : SecurityKey
}