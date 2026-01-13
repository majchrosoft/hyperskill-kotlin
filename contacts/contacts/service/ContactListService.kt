package contacts.service

import contacts.repository.Contacts
import contacts.utils.Output

object ContactListService {
    fun showList(output: Output): Boolean {
        if (Contacts.contacts.isEmpty()) {
            return false
        }

        Contacts.contacts.forEachIndexed { index, contact ->
            output.print("${index + 1}. ${contact.fullName()}")
        }
        return true
    }
}
