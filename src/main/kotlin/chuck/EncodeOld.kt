package chuck

fun encodeOld(input: String): String {

    var sumBinary = ""
    for (char in input) {
        sumBinary += String.format("%7s", Integer.toBinaryString(char.code)).replace(' ', '0')
    }

    val topList = mutableListOf<MutableList<Char>>()
    var lastCheckedChar = ' '
    for (char in sumBinary) {
        if (char == '1' && char != lastCheckedChar) {
            val oneList = mutableListOf<Char>()
            oneList.add(char)
            topList.add(oneList)
            lastCheckedChar = char
        } else if (char == '1') {
            topList[topList.size - 1].add(char)
            lastCheckedChar = char
        } else if (char == '0' && char != lastCheckedChar) {
            val zeroList = mutableListOf<Char>()
            zeroList.add(char)
            topList.add(zeroList)
            lastCheckedChar = char
        } else if (char == '0') {
            topList[topList.size - 1].add(char)
            lastCheckedChar = char
        }
    }

    var code = ""
    for (list in topList) {
        if (list[0] == '1') {
            code += "0 ${"0".repeat(list.size)} "
        } else {
            code += "00 ${"0".repeat(list.size)} "
        }
    }

    return code.trim()
}