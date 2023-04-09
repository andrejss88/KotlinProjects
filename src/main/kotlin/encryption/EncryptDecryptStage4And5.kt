package encryption

import java.io.File

fun main(args: Array<String>) {
    val argMap = mutableMapOf<String, String>()
    val pairList = args.toList().chunked(2)
    for (list in pairList) {
        argMap.put(list[0], list[1])
    }

    val mode = argMap["-mode"] ?: "enc"
    val key = argMap["-key"]?.toInt() ?: 0
    val data = argMap["-data"] ?: ""
    val inFile = argMap["-in"] ?: ""
    val outFile = argMap["-out"] ?: "stdout"

    // prioritize files
    if (inFile.isNotEmpty()) {
        val file = File(inFile)
        val content = file.readText()
        if (outFile == "stdout") {
            val algo = getAlgo(mode)
            println(algo(content, key))
        } else {
            val algo = getAlgo(mode)
            File(outFile).writeText(algo(content, key))
        }
        return
    }

    // if no files - work with CLI input
    val algo = getAlgo(mode)
    println(algo(data, key))
}

fun getAlgo(mode: String): (String, Int) -> String {
    return when (mode) {
        "enc" -> ::encryptSimply2
        "dec" -> ::decryptSimply2
        else -> throw IllegalArgumentException("Unsupported algorithm mode: $mode")
    }
}

fun encryptSimply2(input: String, key: Int): String {
    val builder = StringBuilder()
    for (char in input) {
        builder.append(char + key)
    }
    return builder.toString()
}

fun decryptSimply2(input: String, key: Int): String {
    val builder = StringBuilder()
    for (char in input) {
        builder.append(char - key)
    }
    return builder.toString()
}