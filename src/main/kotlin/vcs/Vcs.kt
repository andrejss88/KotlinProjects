package vcs

fun main(args: Array<String>) {
    val vcs = Vcs()
    if (args.isEmpty()) {
        println(vcs.help); return
    }
    val out = vcs.getCommandDesc(args.first())
    println(out)
}

class Vcs {

    val help = """These are SVCS commands:
config     Get and set a username.
add        Add a file to the index.
log        Show commit logs.
commit     Save changes.
checkout   Restore a file."""

    fun getCommandDesc(command: String): String {
        if (command == "" || command == "--help") return help
        return Command.getDesc(command)
    }
}

enum class Command(val _name: String, val desc: String) {
    CONFIG("config", "Get and set a username."),
    ADD("add", "Add a file to the index."),
    LOG("log", "Show commit logs."),
    COMMIT("commit", "Save changes."),
    CHECKOUT("checkout", "Restore a file.");

    companion object {
        fun getDesc(command: String): String {
            for (comm in Command.values()) {
                if (comm._name == command) {
                    return comm.desc
                }
            }
            return "'$command' is not a SVCS command."
        }
    }
}
