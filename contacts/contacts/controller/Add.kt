package contacts.controller

import contacts.service.AddContact
import contacts.utils.Output

fun addAction(output: Output) {
    AddContact.handle(output)
}
