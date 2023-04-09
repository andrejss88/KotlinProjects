package test.encryption

import encryption.encrypt
import encryption.encryptStage1
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EncryptStage2Test {

    @Test
    fun `encrypt works with one letter - no alphabet overflow`() {
        val key = 1
        assertEquals("b", encrypt("a", key))
        assertEquals("z", encrypt("y", key))
    }

    @Test
    fun `encrypt works with 2+ letters - no alphabet overflow`() {
        val key = 2
        assertEquals("cde", encrypt("abc", key))
        assertEquals("os", encrypt("mq", key))
    }

    @Test
    fun `encrypt does not change chars outside alphabet range`() {
        val key = 20
        assertEquals(" !", encrypt(" !", key))
        assertEquals("$%^-", encrypt("$%^-", key))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - one letter`() {
        val key = 1
        assertEquals("a", encrypt("z", key))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - many letters`() {
        val key = 1
        assertEquals("yza", encrypt("xyz", key))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - large key and many letters`() {
        val key = 2
        assertEquals("zab", encrypt("xyz", key))
    }

    @Test
    fun `encrypt overflows the alphabet correctly - large key and whole sentence`() {
        val key = 5
        assertEquals("bjqhtrj yt mdujwxpnqq", encrypt("welcome to hyperskill", key))
    }
}