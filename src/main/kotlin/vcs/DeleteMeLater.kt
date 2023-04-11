package vcs

//enum class Command(val _name: String, val desc: String) {
//    CONFIG("config", "Get and set a username."),
//    ADD("add", "Add a file to the index."),
//    LOG("log", "Show commit logs."),
//    COMMIT("commit", "Save changes."),
//    CHECKOUT("checkout", "Restore a file.");
//
//    companion object {
//        fun getDesc(command: String): String {
//            for (comm in Command.values()) {
//                if (comm._name == command) {
//                    return comm.desc
//                }
//            }
//            return "'$command' is not a SVCS command."
//        }
//    }
//}