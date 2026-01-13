package contacts

import contacts.repository.Contacts
import contacts.repository.Menu
import contacts.settings.menu
import contacts.utils.ConsoleOutput

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        Contacts.fileName = args[0]
        Contacts.load()
    }
    val output = ConsoleOutput()
    menu {
        all()
    }
    Menu.output = output
    Menu.run()
}