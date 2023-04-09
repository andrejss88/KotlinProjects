package test.encryption

import encryption.decryptSimply
import encryption.encryptSimply
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EncryptStage3Test {

    @Test
    fun `encrypt simple works - just move every char by N code points forward`() {
        val key = 5
        assertEquals("\\jqhtrj%yt%m~ujwxpnqq&", encryptSimply("Welcome to hyperskill!", key))
    }

    @Test
    fun `decrypt simple works - just move every char by N code points backward`() {
        val key = 5
        assertEquals("Welcome to hyperskill!", decryptSimply("\\jqhtrj%yt%m~ujwxpnqq&", key))
    }
}