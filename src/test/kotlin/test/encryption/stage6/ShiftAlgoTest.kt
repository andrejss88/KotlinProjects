package test.encryption.stage6

import encryption.stage6.ShiftAlgo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ShiftAlgoTest {

    // encrypt - only capital letters
    @Test
    fun `encrypt works with one capital letter - no alphabet overflow`() {
        val key = 1
        val uni = ShiftAlgo(key)
        assertEquals("B", uni.encrypt("A"))
    }

    @Test
    fun `encrypt works with multiple capital letter - including overflow`() {
        val key = 2
        val uni = ShiftAlgo(key)
        assertEquals("CDAB", uni.encrypt("ABYZ"))
    }

    // encrypt - only small  letters
    @Test
    fun `encrypt works with one letter - no alphabet overflow`() {
        val key = 1
        val uni = ShiftAlgo(key)
        assertEquals("b", uni.encrypt("a"))
        assertEquals("z", uni.encrypt("y"))
    }

    @Test
    fun `encrypt works with 2+ letters - no alphabet overflow`() {
        val key = 2
        val uni = ShiftAlgo(key)
        assertEquals("cde", uni.encrypt("abc"))
        assertEquals("os", uni.encrypt("mq"))
    }

    @Test
    fun `encrypt does not change chars outside alphabet range`() {
        val key = 20
        val uni = ShiftAlgo(key)
        assertEquals(" !", uni.encrypt(" !"))
        assertEquals("$%^-", uni.encrypt("$%^-"))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - one letter`() {
        val key = 1
        val uni = ShiftAlgo(key)
        assertEquals("a", uni.encrypt("z"))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - many letters`() {
        val key = 1
        val uni = ShiftAlgo(key)
        assertEquals("yza", uni.encrypt("xyz"))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - large key and many letters`() {
        val key = 2
        val uni = ShiftAlgo(key)
        assertEquals("zab", uni.encrypt("xyz"))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - large key and whole sentence`() {
        val key = 5
        val uni = ShiftAlgo(key)
        assertEquals("bjqhtrj yt mdujwxpnqq", uni.encrypt("welcome to hyperskill"))
    }

    @Test
    fun `encrypt works correctly with small, capital letters, and non-alphabet chars`() {
        val key = 5
        val uni = ShiftAlgo(key)
        assertEquals("Bjqhtrj yt mdujwxpnqq!", uni.encrypt("Welcome to hyperskill!"))
    }

    @Test
    fun `decrypt works correctly with - alphabet no overflow`() {
        val key = 1
        val uni = ShiftAlgo(key)
        assertEquals("AB", uni.decrypt("BC"))
        assertEquals("XY", uni.decrypt("YZ"))
    }

    @Test
    fun `decrypt works correctly with - alphabet overflow`() {
        val key = 5
        val uni = ShiftAlgo(key)
        assertEquals("W", uni.decrypt("B"))
    }

    @Test
    fun `decrypt works correctly with small, capital letters, and non-alphabet chars`() {
        val key = 5
        val uni = ShiftAlgo(key)
        assertEquals("Welcome to hyperskill!", uni.decrypt("Bjqhtrj yt mdujwxpnqq!"))
    }
}