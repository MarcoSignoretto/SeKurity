package com.msignoretto.sekurity

import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readBytes
import platform.Foundation.NSData
import platform.Foundation.create

actual fun BinaryData.toByteArray(): ByteArray {
    val data = this.toData()
    return data.bytes!!.readBytes(data.length.toInt())
}
actual fun ByteArray.toBinaryData(): BinaryData {
    return BinaryDataImpl(this.toNSData())
}
private fun BinaryData.toData(): NSData = (this as BinaryDataImpl).data

private fun ByteArray.toNSData() : NSData = memScoped {
    NSData.create(bytes = allocArrayOf(this@toNSData),
        length = this@toNSData.size.toULong())
}