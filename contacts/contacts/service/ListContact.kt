package contacts.service

import contacts.utils.Output
import contacts.repository.Contacts

object ListContact {
    fun handle(output: Output) {
        while (true) {
            if (!ContactListService.showList(output)) {
                output.print("The Phone Book is empty.")
                output.print("")
                return
            }
            output.print("")
            output.print("[list] Enter action (number, back): ")
            val action = readln().lowercase()
            when {
                action == "back" -> {
                    output.print("")
                    return
                }
                action.toIntOrNull() != null -> {
                    val index = action.toInt() - 1
                    if (index in Contacts.contacts.indices) {
                        val contact = Contacts.contacts[index]
                        SearchContact.showContactInfo(output, contact)
                        if (SearchContact.handleRecordMenu(output, contact)) {
                            return
                        }
                        output.print("")
                    }
                }
            }
        }
    }
}
