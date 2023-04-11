package test.vcs

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import vcs.Config
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ConfigCommandTest {

    private val testDir = "vcs_test"
    private val testFile = "test_config.txt"
    private val fullPath = "$testDir/$testFile"

    @BeforeEach
    fun `setup - delete test dir and files`() {
        File(fullPath).delete()
        File(testDir).delete()
    }

    @AfterAll
    fun `tear down - delete test dir and files`() {
        File(fullPath).delete()
        File(testDir).delete()
    }

    private fun config(vararg args: String): Config {
        return Config(args.asList(), testDir, testFile)
    }

    @Test
    fun `when command runs - dir and config file must exist`() {
        config().run()
        assertTrue(File(testDir).exists())
        assertTrue(File(fullPath).exists())
    }

    @Test
    fun `when no user set - return prompt to set user name`() {
        assertEquals("Please, tell me who you are.", config().run())
    }

    @Test
    fun `when user set and invoke command without param - return user name`() {
        config("Max").run()

        val newConfig = config()
        assertEquals("The username is Max.", newConfig.run())
    }

    @Test
    fun `when user set - return user name`() {
        assertEquals("The username is John.", config("John").run())
    }

    @Test
    fun `when user set twice - return latest user name`() {
        config("John").run()

        val newConfig = config("Maria")
        assertEquals("The username is Maria.", newConfig.run())
    }
}