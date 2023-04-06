package chuck

const val ERROR_MSG = "Encoded string is not valid."

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        val input = readln()
        when (input) {
            "encode" -> encodeInput()
            "decode" -> decodeInput()
            "exit" -> {
                println("Bye!"); break
            }

            else -> println("There is no '$input' operation")
        }
    }
}

private fun encodeInput() {
    println("Input string:")
    val output = encode(readln())
    println("Encoded string:")
    println(output)
}

private fun decodeInput() {
    println("Input encoded string:")
    val output = decode(readln())
    if (output != ERROR_MSG) {
        println("Decoded string:")
        println(output)
    } else {
        println(ERROR_MSG)
    }
}

fun decode(input: String): String {

    if (isEncodedStringInvalid(input)) {
        return ERROR_MSG
    }

    val bin = zeroesToBinary(input)
    if (binStringNotMultipleOf7(bin)) {
        return ERROR_MSG
    }

    return from7BitBinaryToString(bin)
}

/**
 * Input rules:
 * 1) It must contain only 0 and spaces
 * 2) Its block count must be even
 * 3) Its 1st and ever other block (3rd, 5th, etc.)  must be either 0 or 00
 */
private fun isEncodedStringInvalid(input: String): Boolean {
    val blocks = input.split(' ')

    // check rules 1 and 2
    if(!input.none { !(it == '0' || it == ' ') } || blocks.size % 2 != 0) {
        return true
    }

    // check rule 3
    for (i in blocks.indices step 2) {
        if (blocks[i].length > 2) {
            return true
        }
    }
    return false
}

private fun binStringNotMultipleOf7(bin: String) = bin.length % 7 != 0

fun from7BitBinaryToString(input: String): String {
    val output = StringBuilder()
    val blockSize = 7
    val list = input.chunked(blockSize)

    for (string in list) {
        output.append(string.toInt(2).toChar())
    }
    return output.toString()
}

fun zeroesToBinary(input: String): String {
    val list = input.split(' ').filter { it.isNotBlank() }

    val sumBinary = StringBuilder()
    var i = 0
    while (i < list.size) {
        val zeroOrOne = if (list[i] == "0") "1" else "0"
        val count = when (val digitCount = list[i + 1].length) {
            1 -> ""
            else -> zeroOrOne.repeat(digitCount - 1)
        }
        sumBinary.append("$zeroOrOne$count")
        i += 2
    }
    return sumBinary.toString()
}

fun encode(input: String): String {

    val binString = to7BitBinaryString(input)

    val code = StringBuilder()
    var lastCheckedChar = ' '
    for (char in binString) {
        when {
            char == '1' && char != lastCheckedChar -> code.append(" 0 0")
            char == '0' && char != lastCheckedChar -> code.append(" 00 0")
            char == '1' || char == '0' -> code.append("0")
        }
        lastCheckedChar = char
    }

    return code.toString().trim()
}

fun to7BitBinaryString(input: String): String {
    var sumBinary = ""
    for (char in input) {
        sumBinary += String.format("%7s", Integer.toBinaryString(char.code)).replace(' ', '0')
    }
    return sumBinary
}