package test.chuck

import chuck.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ChuckTest {

    @Test
    fun decodeWorks() {
        val output = decode("0 0 00 00 0 0 00 000 0 00 00 0 0 0 00 00 0 0 00 0 0 0 00 000000 0 0000 00 000 0 00 00 00 0 00")
        assertEquals("Hi <3", output)
    }

    @Test
    fun whenInputContainsNonZeros_ReturnError() {
        val output = decode("0 0 1 00 0 0 1 000")
        assertEquals("Encoded string is not valid.", output)
    }

    @Test
    // every odd block (1st, 3rd, 5th, etc.) must be 0 or 00 (which map to 1 and 0)
    fun everyOtherBlockMustHaveOneOrTwoZeros_firstOddBlock() {
        val output = decode("000 0 00 00 0000 0 00 000")
        assertEquals("Encoded string is not valid.", output)
    }

    @Test
    fun everyOtherBlockMustHaveOneOrTwoZeros_lastOddBlock() {
        val output = decode("0 0 00 00 0 0 000 000")
        assertEquals("Encoded string is not valid.", output)
    }

    @Test
    fun whenInputBlockCountIsOdd_ReturnError() {
        val output = decode("0 0 00 00 0 0 00")
        assertEquals("Encoded string is not valid.", output)
    }

    @Test
    fun whenDecodedBinaryString_notMultipleOf7_ReturnError() {
        val output = decode("0 0 00 00 0 0 00 00")
        assertEquals("Encoded string is not valid.", output)
    }

    @Test
    fun sevenBitBinaryToStringWorks() {
        val output = from7BitBinaryToString("10000111000011")
        assertEquals("CC", output)
    }

    @Test
    fun zeroesToBinaryWorks() {
        val output = zeroesToBinary("0 0 00 0000 0 000 00 0000 0 00")
        assertEquals("10000111000011", output)
    }

    @ParameterizedTest
    @MethodSource("stringInputBinaryOutput")
    fun to7BitBinaryWorks(input: String, output: String) {
        assertEquals(output, to7BitBinaryString(input))
    }

    private companion object {
        @JvmStatic
        fun stringInputBinaryOutput(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("C", "1000011"),
                Arguments.of("Hi", "10010001101001"),
            )
        }
    }

    @Test
    fun encodeWorks_1() {
        val output = encode("C")
        assertEquals("0 0 00 0000 0 00", output)
    }

    @Test
    fun encodeWorks_2() {
        val output = encode("CC")
        assertEquals("0 0 00 0000 0 000 00 0000 0 00", output)
    }

    @Test
    fun encodeWorks_3() {
        val output = encode("Hi <3")
        assertEquals("0 0 00 00 0 0 00 000 0 00 00 0 0 0 00 00 0 0 00 0 0 0 00 000000 0 0000 00 000 0 00 00 00 0 00", output)
    }
}