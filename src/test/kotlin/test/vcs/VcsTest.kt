package test.vcs

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import vcs.Vcs

class VcsTest {

    @Test
    fun `valid command input returns correct description`() {
        val vcs = Vcs()
        assertEquals("Get and set a username.", vcs.getCommandDesc("config"))
    }

    @Test
    fun `invalid command input returns error message`() {
        val vcs = Vcs()
        assertEquals("'configg' is not a SVCS command.", vcs.getCommandDesc("configg"))
    }
}