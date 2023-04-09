package encryption.stage6

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
    val algoChosen = argMap["-alg"] ?: "shift"

    val algo = if (algoChosen == "shift") ShiftAlgo(key) else UnicodeAlgo(key)

    // prioritize files
    if (inFile.isNotEmpty()) {
        val file = File(inFile)
        val content = file.readText()
        if (outFile == "stdout") {
            val result = if(mode == "enc") algo.encrypt(content) else algo.decrypt(content)
            println(result)
        } else {
            val result = if(mode == "enc") algo.encrypt(content) else algo.decrypt(content)
            File(outFile).writeText(result)
        }
        return
    }

    // if no files - work with CLI input
    val result = if(mode == "enc") algo.encrypt(data) else algo.decrypt(data)
    println(result)
}

interface Algo {
    fun encrypt(input: String): String
    fun decrypt(input: String): String
}

class ShiftAlgo(private val key: Int) : Algo {

    private val small = ('a'..'z')
    private val capital = ('A'..'Z')
    private val alphabetLength = 26

    override fun encrypt(input: String): String {
        val builder = StringBuilder()
        for (char in input) {
            when {
                char !in small && char !in capital -> builder.append(char)
                char in small -> encrypt(builder, char, 'z')
                char in capital -> encrypt(builder, char, 'Z')
            }
        }
        return builder.toString()
    }

    private fun encrypt(builder: StringBuilder, char: Char, lastLetter:Char) {
        when {
            char + key <= lastLetter -> builder.append(char + key)
            else -> {
                val diff = key - (lastLetter - char)
                builder.append(lastLetter - alphabetLength + diff)
            }
        }
    }

    override fun decrypt(input: String): String {
        val builder = StringBuilder()
        for (char in input) {
            when {
                char !in small && char !in capital -> builder.append(char)
                char in small -> decrypt(builder, char, 'a')
                char in capital -> decrypt(builder, char, 'A')
            }
        }
        return builder.toString()
    }

    private fun decrypt(builder: StringBuilder, char: Char, firstLetter:Char) {
        when {
            char - key >= firstLetter -> builder.append(char - key)
            else -> {
                val diff = key - (char - firstLetter)
                builder.append(firstLetter + alphabetLength - diff)
            }
        }
    }
}

class UnicodeAlgo(private val key: Int) : Algo {

    override fun encrypt(input: String): String {
        val builder = StringBuilder()
        input.forEach { builder.append(it + key) }
        return builder.toString()
    }

    override fun decrypt(input: String): String {
        val builder = StringBuilder()
        input.forEach { builder.append(it - key) }
        return builder.toString()
    }
}