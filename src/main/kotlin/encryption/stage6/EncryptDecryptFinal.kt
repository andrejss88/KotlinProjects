package encryption.stage6

import java.io.File

fun main(args: Array<String>) {

    val arg = Arguments(args)

    if (arg.inFile.isNotEmpty()) {
        val content = File(arg.inFile).readText()
        val result = executeAlgo(arg, content)
        if(arg.outFile == arg.stdout) println(result) else File(arg.outFile).writeText(result)

    } else {
        val result = executeAlgo(arg, arg.data)
        println(result)
    }
}

fun executeAlgo(arg: Arguments, input: String) : String {
    val algo = if (arg.algoChosen == ShiftAlgo.name) ShiftAlgo(arg.key) else UnicodeAlgo(arg.key)
    return if (arg.mode == "enc") algo.encrypt(input) else algo.decrypt(input)
}

class Arguments(args: Array<String>) {
    private val argMap = mutableMapOf<String, String>()
    val stdout = "stdout"
    init {
        val pairList = args.toList().chunked(2)
        for (list in pairList) {
            argMap.put(list[0], list[1])
        }
    }

    val mode  = argMap["-mode"] ?: "enc"
    val key = argMap["-key"]?.toInt() ?: 0
    val data = argMap["-data"] ?: ""
    val inFile = argMap["-in"] ?: ""
    val outFile = argMap["-out"] ?: stdout
    val algoChosen = argMap["-alg"] ?: ShiftAlgo.name
}

interface Algo {
    fun encrypt(input: String): String
    fun decrypt(input: String): String
}

class ShiftAlgo(private val key: Int) : Algo {

    companion object {
        const val name = "shift"
    }

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

    private fun encrypt(builder: StringBuilder, char: Char, lastLetter: Char) {
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

    private fun decrypt(builder: StringBuilder, char: Char, firstLetter: Char) {
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

    companion object {
        const val name = "unicode"
    }

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