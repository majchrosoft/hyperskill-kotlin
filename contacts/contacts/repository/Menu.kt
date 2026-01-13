package contacts.repository

import contacts.models.MenuEntry
import contacts.utils.ConsoleOutput
import contacts.utils.Output

object Menu {
    val menuEntries = mutableListOf<MenuEntry>()
    var isRunning = true
    var output: Output = ConsoleOutput()

    fun run() {
        while (isRunning) {
            output.print("[menu] Enter action (add, list, search, count, exit): ")
            val actionName = readln()
            val entry = menuEntries.find { it.name == actionName }
            if (entry != null) {
                entry.action.invoke(output)
                // output.print("") // Removed this as services now handle their own trailing newlines
            }
        }
    }
}
