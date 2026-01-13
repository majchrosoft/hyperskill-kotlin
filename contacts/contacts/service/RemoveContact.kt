package contacts.service

import contacts.repository.Contacts
import contacts.utils.Output

object RemoveContact {
    fun handle(output: Output) {
        if (!ContactListService.showList(output)) {
            output.print("No records to remove!")
            return
        }

        output.print("Select a record: ")
        val indexInput = readln().toIntOrNull()
        if (indexInput == null || indexInput !in 1..Contacts.contacts.size) {
            return
        }
        val contactIndex = indexInput - 1
        Contacts.contacts.removeAt(contactIndex)
        Contacts.save()
        output.print("The record removed!")
    }
}
