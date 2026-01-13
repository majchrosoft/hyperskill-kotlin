package contacts.controller

import contacts.service.EditContact
import contacts.utils.Output

fun editAction(output: Output) {
    // No longer in main menu
    EditContact.handle(output)
}
