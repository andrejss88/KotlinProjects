package encryption

fun main() {
    encrypt(readln(), readln().toInt())
}

fun encrypt(input: String, key: Int): String {
    val alphabetRange = ('a'..'z')
    val alphabetLength = alphabetRange.toList().size
    val builder = StringBuilder()
    for (char in input) {
        when {
            char !in alphabetRange -> builder.append(char)
            char + key <= 'z' -> builder.append(char + key)
            else -> {
                val diff = key - ('z' - char)
                builder.append('z' - alphabetLength + diff)
            }
        }
    }
    return builder.toString()
}