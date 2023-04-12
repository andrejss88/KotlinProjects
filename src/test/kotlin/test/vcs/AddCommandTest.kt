package test.vcs

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import vcs.Add
import vcs.createFile
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddCommandTest {

    private val testDir = "vcs_test"
    private val testFile = "test_index.txt"
    private val fullPath = "$testDir/$testFile"

    @BeforeEach
    fun `setup - delete test dir and files`() {
        File(fullPath).delete()
        File(testDir).delete()
        File("file.txt").createNewFile()
        File("new_file.txt").createNewFile()
    }

    @AfterAll
    fun `tear down - delete test dir and files`() {
        File(fullPath).delete()
        File(testDir).delete()
    }

    private fun add(vararg args: String): Add {
        return Add(args.asList(), createFile(testDir, testFile))
    }

    @Test
    fun `when command runs - dir and config file must exist`() {
        add().run()
        assertTrue(File(testDir).exists())
        assertTrue(File(fullPath).exists())
    }

    @Test
    fun `when no new file specified - return prompt to add a file to the index`() {
        assertEquals("Add a file to the index.", add().run())
    }

    @Test
    fun `when new file specified - return prompt to add a file to the index`() {
        assertEquals("The file 'file.txt' is tracked.", add("file.txt").run())
    }

    @Test
    fun `when 2nd file added - only latest file is reflected in the message as tracked`() {
        add("file.txt").run()
        val newAdd = add("new_file.txt")
        assertEquals("The file 'new_file.txt' is tracked.", newAdd.run())
    }

    @Test
    fun `when no file specified and index has 1 file - return list containing that file`() {
        add("file.txt").run()
        val newAdd = add()
        assertEquals("Tracked files:\nfile.txt", newAdd.run())
    }

    @Test
    fun `when no file specified and index has 2 files - return list containing those files`() {
        add("file.txt").run()
        add("new_file.txt").run()
        val newAdd = add()
        assertEquals("Tracked files:\nfile.txt\nnew_file.txt", newAdd.run())
    }

    @Test
    fun `when added file does not exist - return error message`() {
        val name = "file_doesnt_exist.txt"
        assertEquals("Can't find '$name'.", add(name).run())
    }
}