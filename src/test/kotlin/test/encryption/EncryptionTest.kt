package test.encryption

import encryption.encrypt
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EncryptionTest {

    @Test
    fun `encrypt works with one letter`() {
        assertEquals("z", encrypt("a"))
        assertEquals("a", encrypt("z"))
    }

    @Test
    fun `encrypt works with two letters`() {
        assertEquals("zy", encrypt("ab"))
    }

    @Test
    fun `encrypt works with entire sentence letters`() {
        assertEquals("dv ulfmw z givzhfiv", encrypt("we found a treasure!"))
    }
}