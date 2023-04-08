package encryption

fun main() {
    encrypt("we found a treasure!")
}

fun encrypt(input: String): String {
    val map = ('a'..'z').zip(('z' downTo 'a')).toMap()

    val builder = StringBuilder()
    for (char in input) {
        when (char) {
            in map.keys -> builder.append(map[char].toString())
            ' ' -> builder.append(' ')
        }
    }
    return builder.toString()
}