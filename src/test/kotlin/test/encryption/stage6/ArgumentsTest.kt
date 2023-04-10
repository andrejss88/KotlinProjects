package test.encryption.stage6

import encryption.stage6.Arguments
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ArgumentsTest {

    @Test
    fun `when no mode specified - encode is default`() {
        val args = toArr("-key 5 -alg unicode")
        val arguments = Arguments(args)
        Assertions.assertEquals("enc", arguments.mode)
    }

    @Test
    fun `when no key specified - 0 is default`() {
        val args = toArr("-alg unicode -mode enc")
        val arguments = Arguments(args)
        Assertions.assertEquals(0, arguments.key)
    }

    @Test
    fun `when no data specified - empty string is default`() {
        val args = toArr("-key 5 -mode enc -alg unicode")
        val arguments = Arguments(args)
        Assertions.assertEquals("", arguments.data)
    }

    @Test
    fun `when no out file specified - stdout is default`() {
        val args = toArr("-key 5 -mode enc")
        val arguments = Arguments(args)
        Assertions.assertEquals("stdout", arguments.outFile)
    }

    @Test
    fun `when no algo specified - shift is default`() {
        val args = toArr("-key 5 -mode enc")
        val arguments = Arguments(args)
        Assertions.assertEquals("shift", arguments.algoChosen)
    }

    private fun toArr(args: String): Array<String> {
        return args.split(' ').toTypedArray()
    }

}