package encryption

fun main() {
    when(readln()) {
        "enc" -> encryptSimply(readln(), readln().toInt())
        "dec" -> decryptSimply(readln(), readln().toInt())
    }
}

fun encryptSimply(input: String, key: Int): String {
    val builder = StringBuilder()
    for (char in input) {
        builder.append(char + key)
    }
    return builder.toString()
}

fun decryptSimply(input: String, key: Int): String {
    val builder = StringBuilder()
    for (char in input) {
        builder.append(char - key)
    }
    return builder.toString()
}