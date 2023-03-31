package com.msignoretto.sekurity

internal class BinaryDataImpl(val data: ByteArray): BinaryData

actual fun BinaryData.toByteArray() = (this as BinaryDataImpl).data
actual fun ByteArray.toBinaryData(): BinaryData = BinaryDataImpl(this)