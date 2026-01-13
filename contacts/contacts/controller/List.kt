package contacts.controller

import contacts.service.ListContact
import contacts.utils.Output

fun listAction(output: Output) {
    ListContact.handle(output)
}
