package contacts.controller

import contacts.service.RemoveContact
import contacts.utils.Output

fun removeAction(output: Output) {
    // This is no longer directly in main menu, but keeping it for completeness 
    // or if needed elsewhere.
    RemoveContact.handle(output)
}
