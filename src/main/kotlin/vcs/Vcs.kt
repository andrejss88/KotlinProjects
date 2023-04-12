package vcs

import java.io.File

fun main(args: Array<String>) {
    val vcs = Vcs()
    vcs.execute(args)
}

class Vcs {

    private val help = """These are SVCS commands:
config     Get and set a username.
add        Add a file to the index.
log        Show commit logs.
commit     Save changes.
checkout   Restore a file."""

    private val config = createFile("vcs", "config.txt")
    private val index = createFile("vcs", "index.txt")

    fun execute(args: Array<String>) {
        if (args.isEmpty() || args[0] == "--help") {
            println(help); return
        }
        val params = args.drop(1)
        when (val command = args[0]) {
            "config" -> println(Config(params, config).run())
            "add" -> println(Add(params, index).run())
            "log" -> println("Show commit logs.")
            "commit" -> println("Save changes.")
            "checkout" -> println("Restore a file.")
            else -> println("'$command' is not a SVCS command.")
        }
    }
}

interface Command {
    fun run(): String
}

class Config(_args: List<String>, _file: File) : Command {

    private val args = _args
    private val file = _file

    override fun run(): String {

        val currentUser = file.readText()

        // no params provided
        if (args.isEmpty() && currentUser == "") {
            return "Please, tell me who you are."
        }

        if (args.isEmpty()) {
            return currentUser
        }

        // "user" provided
        val newUserText = "The username is ${args[0]}."
        file.writeText(newUserText)
        return newUserText
    }
}

class Add(_args: List<String>, _file: File) : Command {

    private val args = _args
    private val file = _file

    override fun run(): String {

        val trackedFiles = file.readText()

        // no params provided
        if (args.isEmpty() && trackedFiles == "") {
            return "Add a file to the index."
        }

        val header = "Tracked files:"
        if (args.isEmpty()) {
            return "$header$trackedFiles"
        }

        // file provided
        val newFileName = args[0]
        val newFile = File(newFileName)
        if (!newFile.exists()) {
            return "Can't find '$newFileName'."
        }

        file.appendText("\n$newFileName")
        return "The file '$newFileName' is tracked."
    }
}

fun createFile(dir: String, file: String): File {
    val vcsDir = File(dir)
    if (!vcsDir.exists()) {
        vcsDir.mkdir()
    }

    val configFile = File("$dir/$file")
    if (!configFile.exists()) {
        configFile.createNewFile()
    }
    return configFile
}

