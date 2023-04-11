package vcs

import java.io.File

fun main(args: Array<String>) {
    val vcs = Vcs()
    vcs.execute(args)
}

interface Command {
    fun run(): String
}

class Config(
    _args: List<String>,
    _dir: String = "vcs",
    _file: String = "config.txt"
) : Command {

    private val args = _args
    private val dir = _dir
    private val file = _file

    override fun run(): String {

        val configFile = createConfigFile(dir, file)
        val currentUser = configFile.readText()

        // no params provided
        if (args.isEmpty() && currentUser == "") {
            return "Please, tell me who you are."
        }

        if (args.isEmpty()) {
            return currentUser
        }

        // "user" provided
        val newUser = args[0]
        val newUserText = "The username is $newUser."
        configFile.writeText(newUserText)
        return newUserText
    }

    companion object {
        fun name(): String {
            return "config"
        }
    }
}

private fun createConfigFile(dir: String, file: String): File {
    val vcsDir = File(dir)
    if (!vcsDir.exists()) {
        val result = vcsDir.mkdir()
        println(result)
    }

    val configFile = File("$dir/$file")
    if (!configFile.exists()) {
        configFile.createNewFile()
    }
    return configFile
}

class Add : Command {
    override fun run(): String {
        TODO("Not yet implemented")
        // func:
        // 1) create vcs/ dir if not exists
        // 2) create index.txt if not exists

        // if only "add"
        // empty file -> return Add a file to the index.
        // full file -> return list of files

        // if "add {file}"
        // append {file} to list

    }
}

// keep a list of Commands in Vcs and change getCommandDesc() accordingly

class Vcs {

    private val help = """These are SVCS commands:
config     Get and set a username.
add        Add a file to the index.
log        Show commit logs.
commit     Save changes.
checkout   Restore a file."""

    fun execute(args: Array<String>) {
        if (args.isEmpty()) {
            println(help); return
        }
        val params = args.drop(1)
        when (args[0]) {
            Config.name() -> println(Config(params).run())
        }
    }
}
