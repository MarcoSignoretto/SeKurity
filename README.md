# SeKurity
[ ![Download](https://api.bintray.com/packages/marcosignoretto/maven/com.msignoretto%3Asekurity/images/download.svg) ](https://bintray.com/marcosignoretto/maven/com.msignoretto%3Asekurity/_latestVersion)

Utility functions for data Encryption on Android

This library provides a couple of classes for easy encryption and decryption of data without key management
for API 23 or above.

### Helpers

1. `StringCipher`
2. `FileCipher`

### Ciphers

1. `AESCipher` (bytes or bytes stream manipulation)

## Setup

In your module `build.gradle` include the following dependency

```groovy
implementation 'com.msignoretto.sekurity:sekurity:1.1.0'
```

## Usage

### Helpers

#### StringCipher

```kotlin
val appKeyStore = AppKeyStore(this)
val key = appKeyStore.getOrGenerate()

val stringCipher = StringCipher(cipher = AESCipher())

val plainText = "I'm A plain text"

val encryptedData = stringCipher.encrypt(key, plainText)
val plainTextResult = stringCipher.decrypt(key, encryptedData)

```

#### FileCipher

```kotlin
val appKeyStore = AppKeyStore(this)
val key = appKeyStore.getOrGenerate()

val text = "I'm a text in a file"
val file = File.createTempFile("test", null)
file.writeText(text, Charset.defaultCharset())

val fileOutput = File.createTempFile("testEncrypt", null)
val fileResult = File.createTempFile("testResult", null)

fileCipher.encrypt(key, file, fileOutput)
fileCipher.decrypt(key, fileOutput, fileResult)

```

### Raw Ciphers

#### AESCipher

encrypting bytes
```kotlin
val appKeyStore = AppKeyStore(this)
val key = appKeyStore.getOrGenerate()

val plainData = Random.Default.nextBytes(23)

val cipherData = cipher.encrypt(key, plainData)
val plainDataBack = cipher.decrypt(key, cipherData)

```

encrypting streams
```kotlin
val appKeyStore = AppKeyStore(this)
val key = appKeyStore.getOrGenerate()

val plainData = Random.nextBytes(23)
val plainStream = ByteArrayInputStream(plainData)
val encryptedStream = ByteArrayOutputStream(400)

val plainStreamResult = ByteArrayOutputStream(400)

cipher.encryptStream(key, plainStream, encryptedStream)
cipher.decryptStream(key, ByteArrayInputStream(encryptedStream.toByteArray()), plainStreamResult)

```

## Future improvements

1. Support Android API lower than 23
2. Add `ParcelableCipher`
3. Allow users to specify key alias
4. Support different algorithms








