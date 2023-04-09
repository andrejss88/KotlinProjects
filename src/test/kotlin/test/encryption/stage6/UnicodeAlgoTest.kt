package test.encryption.stage6

import encryption.decryptSimply
import encryption.encryptSimply
import encryption.stage6.ShiftAlgo
import encryption.stage6.UnicodeAlgo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UnicodeAlgoTest {

    @Test
    fun `encrypt works - just move every char by N code points forward`() {
        val key = 5
        val algo = UnicodeAlgo(key)
        assertEquals("\\jqhtrj%yt%m~ujwxpnqq&", algo.encrypt("Welcome to hyperskill!"))
    }

    @Test
    fun `decrypt works - just move every char by N code points backward`() {
        val key = 5
        val algo = UnicodeAlgo(key)
        assertEquals("Welcome to hyperskill!", algo.decrypt("\\jqhtrj%yt%m~ujwxpnqq&"))
    }

    @Test
    fun `encrypting twice produces correct and different results`() {
        val key = 2
        val algo = UnicodeAlgo(key)
        assertEquals("cde", algo.encrypt("abc"))
        assertEquals("[\\", algo.encrypt("YZ"))
    }
}