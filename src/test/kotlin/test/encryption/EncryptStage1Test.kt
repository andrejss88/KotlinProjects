package test.encryption

import encryption.encryptStage1
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class EncryptStage1Test {

    @Test
    fun `encrypt works with one letter`() {
        assertEquals("z", encryptStage1("a"))
        assertEquals("a", encryptStage1("z"))
    }

    @Test
    fun `encrypt works with two letters`() {
        assertEquals("zy", encryptStage1("ab"))
    }

    @Test
    fun `encrypt works with entire sentence letters`() {
        assertEquals("dv ulfmw z givzhfiv", encryptStage1("we found a treasure!"))
    }
}